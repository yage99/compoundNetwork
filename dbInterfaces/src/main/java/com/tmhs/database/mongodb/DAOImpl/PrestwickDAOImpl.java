/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.PrestwickDAO;
import com.tmhs.database.DTO.Prestwick;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class PrestwickDAOImpl extends Collection implements PrestwickDAO {

	/**
	 * @throws UnknownHostException 
	 * 
	 */
	public PrestwickDAOImpl() throws UnknownHostException {
		super("prestwick");
	}

	@Override
	public Prestwick getItemByPosition(String position) {
		DBObject query = new BasicDBObject();
		query.put("position", position);
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext())
			return parse(cur.next(), Prestwick.class);
		return null;
	}

}
