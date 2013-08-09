package ca.dal.cs.dalooc.persistence;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.Video;

import com.mongodb.WriteResult;

public class VideoRepository implements Repository<Video> {

	private MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public List<Video> getAllObjects() {
		return mongoTemplate.findAll(Video.class);
	}

	@Override
	public void saveObject(Video video) {
		this.mongoTemplate.insert(video);
	}

	@Override
	public Video getObject(String[] name, String[] value) {
		Query query = new Query();
		
		for (int i = 0; i < name.length; i++) {
			query.addCriteria(Criteria.where(name[i]).is(value[i]));
		}
		return this.mongoTemplate.findOne(query, Video.class);
	}

	@Override
	public WriteResult updateObject(String id, Object[] nameValue) {
		return this.mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), 
				Update.update((String)nameValue[0], nameValue[1]), 
				Video.class);
	}

	@Override
	public void deleteObject(String name, String value) {
		this.mongoTemplate.remove(new Query(Criteria.where(name).is(value)), Video.class);
	}

	@Override
	public void createCollection() {
		if (!this.mongoTemplate.collectionExists(Video.class)) {
			this.mongoTemplate.createCollection(Video.class);
		}
	}

	@Override
	public void dropCollection() {
		if (this.mongoTemplate.collectionExists(Video.class)) {
			this.mongoTemplate.dropCollection(Video.class);
		}
	}

}
