package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.Audio;

import com.mongodb.WriteResult;

public class AudioRepository implements Repository<Audio> {

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Audio> getAllObjects() {
		return mongoTemplate.findAll(Audio.class);
	}

	@Override
	public void saveObject(Audio audio) {
		this.mongoTemplate.insert(audio);
	}

	@Override
	public Audio getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, Audio.class);
	}

	@Override
	public WriteResult updateObject(String id, Object[] nameValue) {
		return this.mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), 
				Update.update((String)nameValue[0], nameValue[1]), 
				Audio.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)), Audio.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(Audio.class)) {
			this.mongoTemplate.createCollection(Audio.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(Audio.class)) {
			this.mongoTemplate.dropCollection(Audio.class);
		}
	}

}
