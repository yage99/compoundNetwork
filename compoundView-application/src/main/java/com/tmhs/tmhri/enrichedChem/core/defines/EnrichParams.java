/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.defines;

import java.util.List;

import com.tmhs.tmhri.enrichedChem.EnrichConfig;

/**
 * @author ya
 * 
 */
public enum EnrichParams {
	/**
	 * 
	 */
	KEYS("keys", true, List.class, String.class),
	/**
	 * 
	 */
	VALUES("values", true, List.class, Double.class),
	/**
	 * 
	 */
	IMG("imgId"), /**
	 * 
	 */
	SEL_IMG("selectImg"), /**
	 * 
	 */
	EDGE_WIDTH("Protein", true, Double.class, null), /**
	 * 
	 */
	SMILE("smiles", true), /**
			 * 
			 */
	ORIGIN_ID("originId"), /**
			 * 
			 */
	CLUSTER("cluster", false, String.class, null), /**
			 * 
			 */
	CID("cids"), /**
			 * 
			 */
	SHOW_NAME("canonicalName"), /**
			 * 
			 */
	COMP_NAME("compound name", true), /**
			 * 
			 */
	SEARCH_RESULT("search syno names"),
	/**
			 * 
			 */
	SIZE("size", false, Double.class, null),
	/**
			 * 
			 */
	PARENT("parent", false, Long.class, null),

	/**
	 * indicates key to be used to draw pie chart
	 * [{@link EnrichConfig#pieChartPerfix}]:<key1>|<key2>...
	 */
	IMG_KEY("imgKey", false, String.class, null);

	private String value;
	private boolean visible = false;
	private Class<? extends Object> type = String.class;
	private Class<?> listType = String.class;

	/**
	 * @param value
	 * @param visible
	 * @param type
	 */
	EnrichParams(String value, boolean visible, Class<? extends Object> type, Class<?> listType) {
		this.value = value;
		this.visible = visible;
		this.type = type;
		this.listType = listType;
	}

	/**
	 * @deprecated set type for every param
	 * @param value
	 * @param visible
	 */
	EnrichParams(String value, boolean visible) {
		this.value = value;
		this.visible = visible;
	}

	EnrichParams(String value) {
		this(value, false);
	}

	/**
	 * @return the name of this value
	 */
	public String getString() {
		return value;
	}

	/**
	 * @return visible status
	 */
	public boolean visible() {
		return visible;
	}

	/**
	 * @return the parameter's value type
	 */
	public Class<? extends Object> getType() {
		return type;
	}
	
	/**
	 * @return list type
	 */
	public Class<?> getListType() {
		return listType;
	}
}
