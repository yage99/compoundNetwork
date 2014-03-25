/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tmhs.database.DAO.ExperimentsDAO;
import com.tmhs.database.DTO.Experiment;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class ExperimentsDAOImpl extends Collection implements ExperimentsDAO {

	/**
	 * @throws UnknownHostException 
	 * 
	 */
	public ExperimentsDAOImpl() throws UnknownHostException {
		super("experiments");
	}

	@Override
	public List<Experiment> get(int start, int num) {
		DBCursor cur = table.find().skip(start);
		List<Experiment> result = new ArrayList<Experiment>();
		for (int i = 0; i < num && cur.hasNext(); i++) {
			result.add(parse(cur.next(), Experiment.class));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.ExperimentsDAO#getByPosition(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Experiment getByPosition(String libName, String plateNum,
			String position, String cell) {
		DBObject query = new BasicDBObject();
		query.put("cell", cell);
		query.put("libName", libName);
		query.put("plateNum", plateNum);
		query.put("position", position);

		DBCursor cur = table.find(query);
		if (cur.hasNext())
			return parse(cur.next(), Experiment.class);
		return null;
	}

}
