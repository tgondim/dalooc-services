package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.LearningObject;

import com.mongodb.WriteResult;

public class LearningObjectRepository implements Repository<LearningObject> {

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<LearningObject> getAllObjects() {
		return mongoTemplate.findAll(LearningObject.class);
	}

	@Override
	public void saveObject(LearningObject learningObject) {
		this.mongoTemplate.insert(learningObject);
	}

	@Override
	public LearningObject getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, LearningObject.class);
	}

	@Override
	public WriteResult updateObject(String id, Object[] nameValue) {
		return this.mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), 
				Update.update((String)nameValue[0], nameValue[1]), 
				LearningObject.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)), LearningObject.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(LearningObject.class)) {
			this.mongoTemplate.createCollection(LearningObject.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(LearningObject.class)) {
			this.mongoTemplate.dropCollection(LearningObject.class);
		}
	}

}
