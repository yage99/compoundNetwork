/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.config;

import java.util.ArrayList;
import java.util.List;

import com.tmhs.tmhri.enrichedChem.fileparsers.FileParser;
import com.tmhs.tmhri.enrichedChem.ui.ControlPanel;

/**
 * @author ya
 * 
 */
public enum FileType {
	/**
	 * 
	 */
	CDT("cdt", "CDT File", "cluster resulte file"),
	/**
	 * 
	 */
	CLU("cluster", "Seperate File", "simple defile cluster file"),
	/**
	 * 
	 */
	COMP_LIB("clib", "compound lib file", "compound lib file"),
	/**
	 * 
	 */
	CMAP_RESU("xls", "CMAP result", "CMAP process result"),
	/**
	 * 
	 */
	CMAP_DETAIL_RESU("xls", "CMAP detail result", "CMAP detailed result");

	private static List<FileType> registedFileType = new ArrayList<FileType>();

	private String ext;
	private String showName;
	private String desc;
	private FileParser fileParser;

	FileType(String ext, String showName, String desc) {
		this.ext = ext;
		this.desc = desc;
		this.showName = showName;
	}

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return file parser of this file type
	 */
	public FileParser getParser() {
		return fileParser;
	}

	@Override
	public String toString() {
		return this.showName;
	}

	/**
	 * @param fileType
	 */
	public static void registeFileType(FileType fileType) {
		registedFileType = getRegisteredFileType();
		registedFileType.add(fileType);
		ControlPanel.updateFile(registedFileType);
	}

	/**
	 * @return usable file type
	 */
	public static List<FileType> getRegisteredFileType() {
		registedFileType = new ArrayList<FileType>();
		registedFileType.add(FileType.CMAP_DETAIL_RESU);
		return registedFileType;
	}
}
