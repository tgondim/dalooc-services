package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.TestAnswer;

import com.mongodb.WriteResult;

public class TestAnswerRepository implements Repository<TestAnswer> {

	private MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<TestAnswer> getAllObjects() {
		return mongoTemplate.findAll(TestAnswer.class);
	}

	@Override
	public void saveObject(TestAnswer testAnswer) {
		this.mongoTemplate.insert(testAnswer);
	}

	@Override
	public TestAnswer getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, TestAnswer.class);
	}

	@Override
	public WriteResult updateObject(Query query, Update update) {
		return this.mongoTemplate.updateFirst(query, update, TestAnswer.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)),
				TestAnswer.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(TestAnswer.class)) {
			this.mongoTemplate.createCollection(TestAnswer.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(TestAnswer.class)) {
			this.mongoTemplate.dropCollection(TestAnswer.class);
		}
	}

	@Override
	public List<TestAnswer> getObjectList(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.find(query, TestAnswer.class);
	}
}
