/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.SigmaDAO;
import com.tmhs.database.DTO.Sigma;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class SigmaDAOImpl extends Collection implements SigmaDAO {

	/**
	 * 
	 */
	public SigmaDAOImpl() {
		super("sigma");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.SigmaDAO#getDrugByPosition(java.lang.String)
	 */
	@Override
	public Sigma getDrugByPosition(String position) {
		DBObject query = new BasicDBObject();
		query.put("RackNo", position.substring(0, 2));
		query.put("position", position.substring(2, 5));
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext())
			return parse(cur.next(), Sigma.class);
		return null;
	}

}
