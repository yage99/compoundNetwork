/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.tmhs.database.DAO.CompoundSimilarityDAO;
import com.tmhs.database.DTO.CompoundSimilarity;
import com.tmhs.database.Mongo.Collection;

/**
 * @author ya
 * 
 */
public class CompoundSimilarityDAOImpl extends Collection implements
		CompoundSimilarityDAO {

	/**
	 * @throws UnknownHostException 
	 */
	public CompoundSimilarityDAOImpl() throws UnknownHostException {
		super("CompoundSimilarity");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.CompoundSimilarityDAO#getValue(com.tmhs.database
	 * .DTO.CompoundSimilarity)
	 */
	@Override
	public double getValue(CompoundSimilarity cs) {
		DBObject query = new BasicDBObject();
		query.put("cid1", String.valueOf(cs.getCid1()));
		query.put("cid2", String.valueOf(cs.getCid2()));

		DBCursor cur = table.find(query);
		CompoundSimilarity result = null;
		if (cur.hasNext()) {
			result = parse(cur.next(), CompoundSimilarity.class);
			cs.setValue(result.getValue());
			return cs.getValue();
		} else
			return Double.NaN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.CompoundSimilarityDAO#insert(com.tmhs.database.
	 * DTO.CompoundSimilarity)
	 */
	@Override
	public boolean insert(CompoundSimilarity cs) {
		WriteResult wr = table.insert(cover(cs));
		if (wr.getN() == 1)
			return true;
		else
			return false;
	}

}
