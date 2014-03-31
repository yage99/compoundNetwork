/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.SpectrumDAO;
import com.tmhs.database.DTO.Spectrum;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class SpectrumDAOImpl extends Collection implements SpectrumDAO {

	/**
	 * @throws UnknownHostException 
	 */
	public SpectrumDAOImpl() throws UnknownHostException {
		super("spectrum");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.SpectrumDAO#getItemByPosition(java.lang.String)
	 */
	@Override
	public Spectrum getItemByPosition(String position) {
		DBObject query = new BasicDBObject();
		query.put("plate", "111215-" + position.substring(0, 2));
		query.put("position", position.substring(2, 5));
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext())
			return parse(cur.next(), Spectrum.class);
		return null;
	}

}
