/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.tmhs.database.DAO.ProcedExperimentsDAO;
import com.tmhs.database.DTO.ProcedExperiment;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class ProcedExperimentsDAOImpl extends Collection implements
		ProcedExperimentsDAO {

	/**
	 * @throws UnknownHostException 
	 */
	public ProcedExperimentsDAOImpl() throws UnknownHostException {
		super("procedexperiments");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.ProcedExperiments#insert(com.tmhs.database.DTO.
	 * ProcedExperiment)
	 */
	@Override
	public String insert(ProcedExperiment exp) {
		String id = ObjectId.get().toString();
		exp.setId(id);
		table.insert(cover(exp));
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.ProcedExperimentsDAO#getDataByPosition(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public ProcedExperiment getData(String libName, String cell,
			String formatPosition, int expNum, String model) {
		DBObject query = new BasicDBObject();
		DBObject[] or = new BasicDBObject[2];
		or[0] = new BasicDBObject();
		or[0].put("cell", cell);
		or[1] = new BasicDBObject();
		or[1].put("cell", cell + "-1");
		query.put("$or", or);
		query.put("libName", libName);
		query.put("formatPosition", formatPosition);
		query.put("model", model);
		query.put("expNum", String.valueOf(expNum));
		DBCursor cur = table.find(query);
		if (cur != null && cur.hasNext()) {
			return parse(cur.next(), ProcedExperiment.class);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.ProcedExperimentsDAO#getAll(int, int)
	 */
	@Override
	public List<ProcedExperiment> getAll(int start, int limit) {
		DBObject order = new BasicDBObject();
		order.put("formatPosition", "");
		order.put("libName", "");
		DBCursor cur = table.find().skip(start);
		List<ProcedExperiment> result = new ArrayList<ProcedExperiment>();
		for (int i = 0; i < limit && cur.hasNext(); i++) {
			result.add(parse(cur.next(), ProcedExperiment.class));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.ProcedExperimentsDAO#getLib(java.lang.String,
	 * int, int)
	 */
	@Override
	public List<ProcedExperiment> getLib(String lib, int start, int limit) {
		DBObject order = new BasicDBObject();
		order.put("formatPosition", "");
		order.put("cell", "");
		DBObject query = new BasicDBObject();
		query.put("libName", lib);
		DBCursor cur = table.find(query).sort(order).skip(start);
		List<ProcedExperiment> result = new ArrayList<ProcedExperiment>();
		for (int i = 0; i < limit && cur.hasNext(); i++) {
			result.add(parse(cur.next(), ProcedExperiment.class));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.ProcedExperimentsDAO#update(com.tmhs.database.DTO
	 * .ProcedExperiment)
	 */
	@Override
	public boolean update(ProcedExperiment exp) {
		DBObject q = new BasicDBObject();
		q.put("_id", ObjectId.massageToObjectId(exp.getId()));
		WriteResult wr = table.update(q, cover(exp));
		if (wr.getN() == 1)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tmhs.database.DAO.ProcedExperimentsDAO#getDatas(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<ProcedExperiment> getDatas(String libName, String cell,
			String formatPosition) {
		List<ProcedExperiment> result = new ArrayList<ProcedExperiment>();
		DBObject order = new BasicDBObject();
		order.put("model", "");
		order.put("expNum", "");
		DBObject query = new BasicDBObject();
		query.put("cell", cell);
		query.put("libName", libName);
		query.put("formatPosition", formatPosition);
		DBCursor cur = table.find(query).sort(order);
		if (cur != null) {
			while (cur.hasNext()) {
				result.add(parse(cur.next(), ProcedExperiment.class));
			}
			return result;
		} else
			return null;
	}

}
