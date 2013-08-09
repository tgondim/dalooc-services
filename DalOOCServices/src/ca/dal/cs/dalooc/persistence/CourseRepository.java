package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.Course;

import com.mongodb.WriteResult;

public class CourseRepository implements Repository<Course> {

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Course> getAllObjects() {
		return mongoTemplate.findAll(Course.class);
	}

	@Override
	public void saveObject(Course course) {
		this.mongoTemplate.insert(course);
	}

	@Override
	public Course getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, Course.class);
	}

	@Override
	public WriteResult updateObject(String id, Object[] nameValue) {
		return this.mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), 
				Update.update((String)nameValue[0], nameValue[1]), 
				Course.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)), Course.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(Course.class)) {
			this.mongoTemplate.createCollection(Course.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(Course.class)) {
			this.mongoTemplate.dropCollection(Course.class);
		}
	}
	
	public WriteResult updateFirst(Query query, Update update, Class<?> entityClass) {
		return this.mongoTemplate.updateFirst(query, update, entityClass);
	}

}
