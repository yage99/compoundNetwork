package com.tmhs.database.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.EStateFingerprinter;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.similarity.Tanimoto;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.tmhs.tool.yage.Info.NoticeSystem;
import org.tmhs.tool.yage.Info.RunResultStatus;
import org.tmhs.tool.yage.Internet.DownloadCallBack;
import org.tmhs.tool.yage.Internet.DownloadFile;
import org.tmhs.tool.yage.Internet.MyHttpClient;

import com.tmhs.database.DAO.PubChemDrugDAO;
import com.tmhs.database.frame.DAOManager;
import com.tmhs.yage.api.NIH.DownloadStructureCallBack;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;
import com.tmhs.yage.api.NIH.DTO.PubChemInfo2;
import com.tmhs.yage.api.NIH.xml.ResourceManager;

/**
 * @author TMHYXZ6
 * 
 */
public class PubChemWithStream {
	private static PubChemDrugDAO pubChemDao;
	private volatile static boolean streamStatus = true;

	synchronized static boolean enterStream() {
		if (!streamStatus)
			return false;
		try {
			if (pubChemDao != null)
				return true;
			Class.forName("com.tmhs.database.frame.DAOManager");
			pubChemDao = DAOManager.getManager().getDAO(PubChemDrugDAO.class);
			NoticeSystem.getInstance().debug(
					"found database, entering stream model");
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			streamStatus = false;
			NoticeSystem.getInstance().debug("have not connected to a stream");
			return false;
		} catch (Exception e) {
			// e.printStackTrace();
			streamStatus = false;
			NoticeSystem
					.getInstance()
					.warning(
							"error when ertering stream model. Stay in connect with pubchem.");
			return false;
		}

		return true;
	}

	/**
	 * @param orgchemName
	 * @return a list contains all possible compounds
	 * @throws Exception
	 */
	public static List<PubChemDrug> searchCompound(String orgchemName)
			throws Exception {
		// System.out.print(".");
		List<PubChemDrug> result = new ArrayList<PubChemDrug>();

		String chemName = orgchemName.replace(" ", "%20");
		Response resps;
		try {
			// get the result page
			resps = Jsoup
					.connect(
							"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pccompound&term="
									+ chemName).timeout(20000).execute();
		} catch (IOException e) {
			e.printStackTrace();
			if (e.getMessage().equals("Read timed out")) {
				throw new Exception("Read timed out");
			}
			if (e.getMessage().equals("HTTP error fetching URL")) {
				throw new Exception("Service Unavaiable");
			} else {
				throw new Exception("can't find: " + chemName + "\nMessage: "
						+ e.getMessage());
			}
		}

		try {
			// got the result page
			Document doc = resps.parse();
			int num = Integer.parseInt(doc.select("Count").first().text());
			if (num == 0) {
				return result;
			}

			Elements cids = doc.select("Id");
			PubChemDrug bestMatch = null;
			for (Element cid : cids) {
				PubChemDrug drug = new PubChemDrug();
				drug.setCid(cid.text());
				result.add(drug);

				PubChemInfo2 info = chemInfo(cid.text());
				drug.setSynos(info.getSynonyms());
				drug.setStructure("http://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?t=l&cid="
						+ cid.text());
				drug.setSmiles(info.getCanonicalSmiles());

				if (info.getSynonyms().toString().contains(orgchemName))
					bestMatch = drug;
				if (enterStream()) {
					if (pubChemDao.find(cid.text()) == null) {
						pubChemDao.insert(drug);
					}
				}

			}
			if (bestMatch != null) {
				result.remove(bestMatch);
				result.add(bestMatch);
			}

		} catch (IOException e) {
			throw new Exception("parse-\n" + resps.headers() + resps.body());
		} catch (NullPointerException e) {
			throw new Exception(
					"number-error: "
							+ ":url-http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pccompound&term="
							+ chemName + "\n" + resps.body());
		} catch (NumberFormatException e) {
			throw new Exception(
					"number-error: "
							+ ":url-http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pccompound&term="
							+ chemName + "\n" + resps.body());
		}
		return result;
	}

	/**
	 * @param cid
	 * @return pubchem information v2.0
	 */
	static PubChemInfo2 chemInfo(String cid) {
		// System.out.println("getting chem info");
		PubChemInfo2 info = new PubChemInfo2();

		Response resps;
		try {
			resps = Jsoup
					.connect(
							"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pccompound&id="
									+ cid + "&version=2.0").timeout(10000)
					.execute();
		} catch (IOException e) {
			if (e.getMessage().equals("Read timed out")) {
				NoticeSystem.getInstance().err(cid + "Read time out");
				info.setStatus(RunResultStatus.READTIMEOUT);
				return info;
			} else {
				NoticeSystem.getInstance().err(
						"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pccompound&id="
								+ cid + "&version=2.0");
				info.setStatus(RunResultStatus.SEARCHRESULEERROR);
				return info;
			}
		}

		try {
			Document doc = resps.parse();
			Element synonymList = doc.select("SynonymList").first();
			Elements synonyms = synonymList.select("String");
			List<String> list = new ArrayList<String>();
			for (Element synonym : synonyms) {
				list.add(synonym.text());
			}
			info.setSynonyms(list);

			Element smiles = doc.select("CanonicalSmiles").first();
			info.setCanonicalSmiles(smiles.text());
		} catch (IOException e) {
			NoticeSystem.getInstance().err("parse error-" + resps.body());
			info.setStatus(RunResultStatus.HTMLPARSEERROR);
			return info;
		} catch (NullPointerException e) {
			NoticeSystem.getInstance().err("parse error-" + resps.body());
			info.setStatus(RunResultStatus.NUMBERFORMAT);
			return info;
		} catch (NumberFormatException e) {
			NoticeSystem.getInstance().err("parse error-" + resps.body());
			info.setStatus(RunResultStatus.NUMBERFORMAT);
			return info;
		}

		info.setStatus(RunResultStatus.OK);
		return info;
	}

	/**
	 * @param cid
	 * @return inputStream
	 * @throws IOException
	 */
	public static InputStream getStructureImg(String cid) throws IOException {
		// System.out.println("saving pic");
		URL url;
		url = new URL(
				"http://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?t=l&cid="
						+ cid);
		URLConnection con = url.openConnection();
		return con.getInputStream();
	}

	/**
	 * @param cid
	 * @return drug
	 */
	public static PubChemDrug getDrugByCid(String cid) {
		PubChemDrug drug = null;
		if (enterStream()) {
			if ((drug = pubChemDao.find(cid)) != null)
				return drug;
		}
		drug = new PubChemDrug();
		PubChemInfo2 info = chemInfo(cid);
		drug.setCid(cid);
		drug.setSynos(info.getSynonyms());
		drug.setStructure("http://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?t=l&cid="
				+ cid);

		return drug;
	}

	/**
	 */
	public enum CompoundDescType {
		/**
		 * smiles data format for compound
		 */
		SMILES("smiles"),
		/**
		 * sdf data format for compound
		 */
		SDF("sdf");

		String value;

		CompoundDescType(String value) {
			this.value = value;
		}

	}

	/**
	 * @param cids
	 * @param descType
	 * @throws Exception
	 */
	public static void downLoadCompoundStructure(List<String> cids,
			CompoundDescType descType) throws Exception {
		downLoadCompoundStructure(cids, descType, false, null, true);
	}

	/**
	 * see
	 * {@link #downLoadCompoundStructure(List, CompoundDescType, boolean, DownloadStructureCallBack, boolean)}
	 */
	@SuppressWarnings("javadoc")
	public static void downLoadCompoundStructure(List<String> cids,
			final CompoundDescType descType, boolean zip,
			final DownloadStructureCallBack callback) throws Exception {
		downLoadCompoundStructure(cids, descType, zip, callback, true);
	}

	/**
	 * @param cids
	 * @param descType
	 * @param zip
	 * @param callback
	 * @param multiThread
	 * @throws Exception
	 */
	public static void downLoadCompoundStructure(List<String> cids,
			final CompoundDescType descType, boolean zip,
			final DownloadStructureCallBack callback, final boolean multiThread)
			throws Exception {
		final Map<String, String> structures = new HashMap<String, String>();
		// XXX finish stream model
		if (enterStream()) {
			pubChemDao = DAOManager.getManager().getDAO(PubChemDrugDAO.class);
			for (String cid : cids) {
				PubChemDrug drug = pubChemDao.find(cid);
				if (drug != null && drug.getSmiles() != null) {
					structures.put(drug.getCid(), drug.getSmiles());
				}
			}
			cids.removeAll(structures.keySet());
			if (cids.size() == 0) {
				new Thread() {
					public void run() {
						callback.finish(structures);
					}
				}.start();
				return;
			}
		}
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(ResourceManager
				.getXML("CidsForSdfDownload.xml"));
		// set download format
		org.dom4j.Element type = (org.dom4j.Element) doc
				.selectSingleNode("//PCT-Download_format");
		type.addAttribute("value", descType.value);
		if (zip)
			((org.dom4j.Element) doc
					.selectSingleNode("//PCT-Download_compression"))
					.addAttribute("value", "gzip");

		org.dom4j.Element idListNode = (org.dom4j.Element) doc
				.selectSingleNode("//PCT-ID-List_uids");
		for (String cid : cids) {
			idListNode.addElement("PCT-ID-List_uids_E").setText(cid);
		}

		// the request xml string has been built
		NoticeSystem.getInstance().debug(doc.asXML());

		// create a thread to wait for result
		Thread don = new DownloadSdfThread(doc, new DownloadCallBack() {

			@Override
			public void finish(File tempFile) {
				// update database
				if (descType == CompoundDescType.SMILES) {
					InputStream is;
					Map<String, String> newparse = new HashMap<String, String>();
					try {
						is = new FileInputStream(tempFile);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(is));
						String line;
						while ((line = reader.readLine()) != null) {
							String[] probs = line.split("\t");
							structures.put(probs[0], probs[1]);
							newparse.put(probs[0], probs[1]);
						}
						reader.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (enterStream()) {
						try {
							if (pubChemDao == null)
								pubChemDao = DAOManager.getManager().getDAO(
										PubChemDrugDAO.class);
							for (String key : newparse.keySet()) {
								PubChemDrug pubchem = pubChemDao.find(key);
								if (pubchem != null) {
									pubchem.setSmiles(newparse.get(key));
									if (!pubChemDao.update(pubchem)) {
										NoticeSystem.getInstance().err(
												"update database error");
									}
								} else {
									pubchem = new PubChemDrug();
									pubchem.setCid(key);
									pubchem.setSmiles(newparse.get(key));
									pubChemDao.insert(pubchem);
								}
							}
						} catch (ClassNotFoundException e) {
							NoticeSystem.getInstance().debug("no database");
						} catch (Exception e) {
							NoticeSystem.getInstance().err(
									"enter fail, database system config error:"
											+ e.getMessage());
						}
					}
				}
				if (callback != null) {
					callback.finish(structures);
				}
			}

		});
		if (multiThread)
			don.start();
		else
			don.run();
	}

	/**
	 * @param smiles1
	 * @param smiles2
	 * @return the similarity between two compounds
	 * @throws Exception
	 */
	public double getCompoundSimilarity(String smiles1, String smiles2)
			throws Exception {
		EStateFingerprinter fp = new EStateFingerprinter();
		SmilesParser sp = new SmilesParser(
				DefaultChemObjectBuilder.getInstance());
		IMolecule iMolecule1, iMolecule2;
		try {
			iMolecule1 = sp.parseSmiles(smiles1);
		} catch (InvalidSmilesException e) {
			throw new Exception("invalid similes Exception: " + smiles1);
		}
		try {
			iMolecule2 = sp.parseSmiles(smiles2);
		} catch (InvalidSmilesException e) {
			throw new Exception("invalid similes Exception: " + smiles2);
		}

		IMolecule molH1 = (IMolecule) iMolecule1.clone();
		IMolecule molH2 = (IMolecule) iMolecule2.clone();
		if (molH1 == null)
			molH1 = iMolecule1;
		else
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(molH1);
		if (molH2 == null)
			molH2 = iMolecule2;
		else
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(molH2);

		BitSet fp1 = fp.getFingerprint(molH1);
		BitSet fp2 = fp.getFingerprint(molH2);

		double score = Double.MIN_VALUE;
		if (fp1 != null && fp2 != null) {
			try {
				score = Tanimoto.calculate(fp1, fp2);
			} catch (Exception e) {
				System.err.println("Tanimoto calculation failed: " + e);
				score = 0.0;
			}
		} else {
			System.err.println("Null fingerprint!");
		}
		if (score == Double.MIN_VALUE)
			score = 0.0;

		return score;

	}
}

class DownloadSdfThread extends Thread {
	private org.dom4j.Document doc;
	private DownloadCallBack callback;

	public DownloadSdfThread(org.dom4j.Document doc, DownloadCallBack callback) {
		this.doc = doc;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			// submit task
			org.dom4j.Document res = DocumentHelper.parseText(MyHttpClient
					.postXML("http://pubchem.ncbi.nlm.nih.gov/pug/pug.cgi",
							doc.asXML()));

			NoticeSystem.getInstance().debug(res.asXML());
			// get the response status
			String status = ((org.dom4j.Element) res
					.selectSingleNode("//PCT-Status")).attributeValue("value");
			if (!(status.equals("success") || status.equals("queued") || status
					.equals("running"))) {
				throw new IOException("request submit error:" + res.asXML());
			}

			SAXReader reader = new SAXReader();

			if (res.selectSingleNode("//PCT-Download-URL_url") == null) {
				String id = res.selectSingleNode("//PCT-Waiting_reqid")
						.getText();

				org.dom4j.Document submitXml = reader.read(ResourceManager
						.getXML("submitId.xml"));
				submitXml.selectSingleNode("//PCT-Request_reqid").setText(id);

				NoticeSystem.getInstance().debug(submitXml.asXML());
				do {
					sleep(2000);
					NoticeSystem.getInstance().notice(
							"pending compound structure: try");
					res = DocumentHelper.parseText(MyHttpClient.postXML(
							"http://pubchem.ncbi.nlm.nih.gov/pug/pug.cgi",
							submitXml.asXML()));
					status = res.selectSingleNode("//PCT-Status").getText();
					if (!(status.equals("success") || status.equals("queued") || status
							.equals("running"))) {
						NoticeSystem.getInstance().notice(
								"return status: " + status
										+ " while downloading sdf");
						this.interrupt();
					}
				} while (res.selectSingleNode("//PCT-Download-URL_url") == null);
			}

			String url = res.selectSingleNode("//PCT-Download-URL_url")
					.getText();
			File tempFile = new File(url.substring(url.lastIndexOf('/') + 1));
			new DownloadFile().ftpDownload(url, tempFile,
					"ftp-private.ncbi.nlm.nih.gov", callback);
		} catch (InterruptedException e) {
			NoticeSystem.getInstance().notice(
					"download thread interrupted unexpectly");
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
