package com.tmhs.database.main;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.EStateFingerprinter;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.similarity.Tanimoto;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.tmhs.tool.yage.Info.NoticeSystem;
import com.tmhs.database.DAO.PubChemDrugDAO;
import com.tmhs.database.frame.DAOManager;
import com.tmhs.yage.api.NIH.PubChemDB;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;

/**
 * @author TMHYXZ6
 * 
 */
public class PubChemWithStream {
	private static PubChemDrugDAO pubChemDao;
	private volatile static Boolean streamStatus = null;

	synchronized static boolean enterStream() {
		if (streamStatus != null)
			return streamStatus;
		try {
			pubChemDao = DAOManager.getManager().getDAO(PubChemDrugDAO.class);
			if (pubChemDao != null)
				streamStatus = true;
			pubChemDao.find("11");
			NoticeSystem.getInstance().debug(
					"found database, entering stream model");
			return streamStatus;
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			streamStatus = false;
			NoticeSystem.getInstance().debug("have not connected to a stream");
		} catch (Exception e) {
			streamStatus = false;
			NoticeSystem
					.getInstance()
					.warning(
							"error when ertering stream model. Stay in connect with pubchem.");
		}
		return streamStatus;

	}

	/**
	 * @param orgchemName
	 * @return a list contains all possible compounds
	 * @throws Exception
	 */
	public static List<PubChemDrug> searchCompound(String orgchemName)
			throws Exception {
		List<PubChemDrug> result = new ArrayList<PubChemDrug>();
		// System.out.print(".");
		List<String> compounds = PubChemDB.searchCompound(orgchemName);

		int num = compounds.size();
		if (num == 0) {
			return result;
		}

		PubChemDrug bestMatch = null;
		for (String cid : compounds) {
			PubChemDrug drug = null;
			if (enterStream()) {
				drug = pubChemDao.find(cid);
			}
			if (drug == null) {
				drug = PubChemDB.getPubChemDrug(cid);
				if (enterStream())
					pubChemDao.insert(drug);
			}

			if (drug.getSynos().contains(orgchemName))
				bestMatch = drug;

		}
		if (bestMatch != null) {
			result.remove(bestMatch);
			result.add(bestMatch);
		}

		return result;
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