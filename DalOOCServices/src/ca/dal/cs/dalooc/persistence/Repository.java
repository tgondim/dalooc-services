package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public interface Repository<T> {

	public List<T> getAllObjects();

	public List<T> getObjectList(String[] name, String[] value);
	
	public void saveObject(T object);

	public T getObject(String[] name, String[] value);
	
	public WriteResult updateObject(Query query, Update update);
	
	public void deleteObject(String name, String value);
	
	public void createCollection();
	
	public void dropCollection();

}
