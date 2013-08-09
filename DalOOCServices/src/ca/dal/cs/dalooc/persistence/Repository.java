package ca.dal.cs.dalooc.persistence;

import java.util.List;

import com.mongodb.WriteResult;

public interface Repository<T> {

	public List<T> getAllObjects();
	
	public void saveObject(T object);

	public T getObject(String[] name, String[] value);
	
	public WriteResult updateObject(String id, Object[] nameValue);
	
	public void deleteObject(String name, String value);
	
	public void createCollection();
	
	public void dropCollection();

}
