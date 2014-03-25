/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.TocrisDAO;
import com.tmhs.database.DTO.Tocris;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class TocrisDAOImpl extends Collection implements TocrisDAO {

	/**
	 */
	public TocrisDAOImpl() {
		super("tocris");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.TocrisDAO#getItemByPosition(java.lang.String)
	 */
	@Override
	public Tocris getItemByPosition(String position) {
		DBObject query = new BasicDBObject();
		query.put("platNum",
				String.valueOf(Integer.parseInt(position.substring(0, 2))));
		query.put("position", position.substring(2, 5));
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext())
			return parse(cur.next(), Tocris.class);
		return null;
	}

}
