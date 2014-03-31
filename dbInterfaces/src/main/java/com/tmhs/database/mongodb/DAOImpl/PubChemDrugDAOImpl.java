/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.PubChemDrugDAO;
import com.tmhs.database.Mongo.Collection;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;
/**
 * @author TMHYXZ6
 * 
 */
public class PubChemDrugDAOImpl extends Collection implements PubChemDrugDAO {

	/**
	 * @throws UnknownHostException 
	 */
	public PubChemDrugDAOImpl() throws UnknownHostException {
		super("PubChem");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.PubChemDrugDAO#insert(org.tmhs.yage.api.NIH.DTO
	 * .PubChemDrug)
	 */
	@Override
	public String insert(PubChemDrug drug) {
		ObjectId id = ObjectId.get();
		drug.setId(id.toString());
		table.insert(cover(drug));
		return id.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.PubChemDrugDAO#find(java.lang.String)
	 */
	@Override
	public PubChemDrug find(String cid) {
		DBObject query = new BasicDBObject();
		query.put("cid", cid);
		DBCursor cur = table.find(query);
		if (cur.hasNext())
			return parse(cur.next(), PubChemDrug.class);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.PubChemDrugDAO#update(com.tmhs.database.DTO.PubChemDrug
	 * )
	 */
	@Override
	public boolean update(PubChemDrug drug) {
		return update(cover(drug), false, false, this);
	}

}
