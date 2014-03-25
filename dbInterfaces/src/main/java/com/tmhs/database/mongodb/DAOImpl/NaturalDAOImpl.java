/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.NaturalDAO;
import com.tmhs.database.DTO.Natural;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class NaturalDAOImpl extends Collection implements NaturalDAO {

	/**
	 * @throws UnknownHostException 
	 */
	public NaturalDAOImpl() throws UnknownHostException {
		super("natural");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.NatureDAO#getItemByPosition(java.lang.String)
	 */
	@Override
	public Natural getItemByPosition(String position) {
		DBObject query = new BasicDBObject();
		query.put("plateNum", position.substring(0, 2));
		query.put("wellNum", position.substring(2));
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext())
			return parse(cur.next(), Natural.class);
		return null;
	}

}
