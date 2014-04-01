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
import com.tmhs.database.DAO.DrugDAO;
import com.tmhs.database.DTO.Drug;
import com.tmhs.database.Mongo.Collection;

/**
 * @author TMHYXZ6
 * 
 * @deprecated this also useless. will be remove from next release.
 * 
 */
public class DrugDAOImpl extends Collection implements DrugDAO {

	/**
	 * @throws UnknownHostException
	 * 
	 */
	public DrugDAOImpl() throws UnknownHostException {
		super("drug");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.DrugDAO#insert(com.tmhs.database.DTO.Drug)
	 */
	@Override
	public String insert(Drug drug) {
		String id = ObjectId.get().toString();
		drug.setId(id);
		DBObject obj = cover(drug);
		table.insert(obj);
		return id;
	}

	@Override
	public List<Drug> getAll(int start, int end) {
		DBCursor cur = table.find();
		List<Drug> result = new ArrayList<Drug>();
		while (cur.hasNext()) {
			result.add(parse(cur.next(), Drug.class));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.DrugDAO#getDrugByPosition(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Drug getDrugByPosition(String libName, String position) {
		DBObject query = new BasicDBObject();
		query.put("libName", libName);
		query.put("position", position);
		DBCursor cur = table.find(query);
		if (cur.hasNext())
			return parse(cur.next(), Drug.class);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.DrugDAO#getDrugById(java.lang.String)
	 */
	@Override
	public Drug getDrugById(String id) {
		DBObject query = new BasicDBObject();
		query.put("_id", ObjectId.massageToObjectId(id));
		DBCursor cur = table.find(query);
		if (cur.hasNext())
			return parse(cur.next(), Drug.class);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.DrugDAO#update(com.tmhs.database.DTO.Drug)
	 */
	@Override
	public boolean update(Drug drug, boolean upsert) {
		return update(cover(drug), upsert, false, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.DrugDAO#getDrugByIndex(java.lang.String)
	 */
	@Override
	public Drug getDrugByIndex(String index) {
		DBObject query = new BasicDBObject();
		query.put("sysIndex", index);
		DBCursor cur = table.find(query);
		if (cur.hasNext())
			return parse(cur.next(), Drug.class);
		return null;
	}

}
