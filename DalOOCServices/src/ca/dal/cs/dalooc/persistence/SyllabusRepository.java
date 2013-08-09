package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.Syllabus;

import com.mongodb.WriteResult;

public class SyllabusRepository implements Repository<Syllabus> {

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Syllabus> getAllObjects() {
		return mongoTemplate.findAll(Syllabus.class);
	}

	@Override
	public void saveObject(Syllabus syllabus) {
		this.mongoTemplate.insert(syllabus);
	}

	@Override
	public Syllabus getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, Syllabus.class);
	}

	@Override
	public WriteResult updateObject(String id, Object[] nameValue) {
		return this.mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), 
				Update.update((String)nameValue[0], nameValue[1]), 
				Syllabus.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)), Syllabus.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(Syllabus.class)) {
			this.mongoTemplate.createCollection(Syllabus.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(Syllabus.class)) {
			this.mongoTemplate.dropCollection(Syllabus.class);
		}
	}

}
