/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.defines;

/**
 * @author ya
 * 
 */
public class InputParams {
	/**
	 * 
	 */
	public static Params idCover = Params.COVER_BY_NAME;
	/**
	 * 
	 */
	public static Double minEdge = 6.0;

	/**
	 * 
	 */
	public static boolean database_stream = true;

	/**
	 * 
	 */
	public static FileType sourceFile_type = FileType.CMAP_DETAIL_RESU;

	/**
	 * @author ya
	 * 
	 */
	public enum Params {
		/**
		 * this indicates the id in the data tag is an cid used by pubchem
		 */
		PUBCHEM_IMMIDIATE("PUBCHEM_SOURCE",
				"get smiles string from pubchem immediatily"),
		/**
		 * there is a one-by-one cover file
		 */
		COMPOUND_COVER("FILE_SOURCE", "get CID string from a file"),
		/**
		 * local database has information about cover origin id to cid
		 */
		LOCALDATABASE_CID_COVER("LOCALDATABASE_CID_COVER",
				"get cid from local database"),

		/**
		 * this will ignore the origin id information. The system search
		 * compound information from then pubChem database itself.
		 */
		COVER_BY_NAME("SEARCH PUBCHEM TO COVER",
				"search the pubChem database to get the cid");

		private String desc;
		private String name;

		Params(String name, String desc) {
			this.desc = desc;
			this.name = name;
		}

		/**
		 * @return name of the param
		 */
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}
	}

}
