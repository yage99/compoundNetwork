/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.PhytochemDAO;
import com.tmhs.database.DTO.Phytochem;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class PhytochemDAOImpl extends Collection implements PhytochemDAO {

	/**
	 * 
	 */
	public PhytochemDAOImpl() {
		super("phytochem");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.PhytochemDAO#getItemByPostion(java.lang.String)
	 */
	@Override
	public Phytochem getItemByPostion(String position) {
		DBObject query = new BasicDBObject();
		query.put("position", position);
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext())
			return parse(cur.next(), Phytochem.class);
		return null;
	}

}
