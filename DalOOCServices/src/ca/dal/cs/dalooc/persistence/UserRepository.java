package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.User;

import com.mongodb.WriteResult;

public class UserRepository implements Repository<User> {

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<User> getAllObjects() {
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public void saveObject(User user) {
		this.mongoTemplate.insert(user);
	}

	@Override
	public User getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, User.class);
	}

	@Override
	public WriteResult updateObject(Query query, Update update) {
		return this.mongoTemplate.updateFirst(query, update, User.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)), User.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(User.class)) {
			this.mongoTemplate.createCollection(User.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(User.class)) {
			this.mongoTemplate.dropCollection(User.class);
		}
	}
	
	@Override
	public List<User> getObjectList(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.find(query, User.class);
	}
}
