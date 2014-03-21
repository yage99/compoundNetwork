/**
 * 
 */
package com.tmhs.database.mongodb.DAOImpl;

import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.tmhs.database.DAO.FileManagerDAO;
import com.tmhs.database.Mongo.Collection;
/**
 * @author TMHYXZ6
 * 
 */
public class FileManagerDAOImpl extends Collection implements FileManagerDAO {

	/**
	 */
	public FileManagerDAOImpl() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.FileManagerDAO#saveFile(java.io.InputStream)
	 */
	@Override
	public String saveFile(InputStream is, String fileName, boolean close) {
		String sid;
		if ((sid = findFile(fileName)) != null)
			return sid;
		;
		GridFSInputFile inputFile = fs.createFile(is, close);
		ObjectId id = ObjectId.get();
		inputFile.setId(id);
		inputFile.setFilename(fileName);
		inputFile.save();
		return id.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.FileManagerDAO#findFile(java.lang.String)
	 */
	@Override
	public String findFile(String fileName) {
		List<GridFSDBFile> cur = fs.find(fileName);
		if (cur != null && cur.size() > 0) {
			return cur.get(0).getId().toString();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tmhs.database.DAO.FileManagerDAO#getFile(java.lang.String)
	 */
	@Override
	public InputStream getFile(String fileName) {
		List<GridFSDBFile> cur = fs.find(fileName);
		if (cur != null && cur.size() > 0) {
			return cur.get(0).getInputStream();
		}
		return null;
	}

}
