/**
 * 
 */
package com.tmhs.yage.api.NIH.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import com.tmhs.yage.api.NIH.PubChemCompound;
import com.tmhs.yage.api.NIH.PubChemCompound.CompoundDescType;


/**
 * @author TMHYXZ6
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> cids = new ArrayList<String>();
		for (int i = 1; i < 1000; i++)
			cids.add(String.valueOf(i));
		try {
			PubChemCompound.downLoadCompoundStructure(cids,
					CompoundDescType.SMILES);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
