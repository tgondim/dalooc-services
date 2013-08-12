package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@org.springframework.data.mongodb.core.mapping.Document
public class LearningObject implements Serializable {
	
	public enum Status {
		TODO,
		DOING,
		DONE
	}

	private static final long serialVersionUID = -7926340467663710908L;

	@Id
	private String _id;

	private String name;
	
	private String description;

	private ArrayList<Video> videoList;
	
	private ArrayList<Audio> audioList;
	
	private ArrayList<Document> documentList;

	private ArrayList<TestQuestion> testQuestionList;
	
	private int order;
	
	@Transient
	private LearningObject.Status status;

	public LearningObject() {
		super();
		this._id = new ObjectId().toString();
		this.videoList = new ArrayList<Video>();
		this.audioList = new ArrayList<Audio>();
		this.documentList = new ArrayList<Document>();
		this.testQuestionList = new ArrayList<TestQuestion>();
	}
	
	public LearningObject(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}
	
	public LearningObject(String id, String name, String description) {
		this(name, description);
		this._id = id;
	}
	
	public LearningObject(String id, 
			String name, 
			String description, 
			ArrayList<Video> videoList, 
			ArrayList<Audio> audioList, 
			ArrayList<Document> documentList,
			ArrayList<TestQuestion> testQuestionsList, int order) {
		this(id, name, description);
		this.videoList = videoList;
		this.audioList = audioList;
		this.documentList = documentList;
		this.testQuestionList = testQuestionsList;
		this.order = order;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public ArrayList<Audio> getAudioList() {
		return audioList;
	}

	public ArrayList<Document> getDocumentList() {
		return documentList;
	}
	
	public ArrayList<TestQuestion> getTestQuestionList() {
		return testQuestionList;
	}

	public void setTestQuestionList(ArrayList<TestQuestion> testQuestionList) {
		this.testQuestionList = testQuestionList;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public LearningObject.Status getStatus() {
		return status;
	}

	public void setStatus(LearningObject.Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof LearningObject)) {
			return false;
		}
		return ((LearningObject)o)._id.equals(this._id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this._id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[\"_id\" : \"" + this._id + "\"");
		sb.append(", \"name\" : \"" + this.name + "\"");
		sb.append(", \"description\" : \"" + this.description + "\"");
		
		if ( this.videoList != null) {
			String videoList = this.videoList.toString();
			sb.append(", \"videoList\" : \"" + videoList + "\"");
		}
		if (this.audioList != null) {
			String audioList = this.audioList.toString();
			sb.append(", \"audioList\" : \"" + audioList + "\"");
		}
		if (this.documentList != null) {
			String documentList = this.documentList.toString();
			sb.append(", \"documentList\" : \"" + documentList + "\"");
		}
		if (this.testQuestionList != null) {
			String testQuestionsList = this.testQuestionList.toString();
			sb.append(", \"testQuestionList\" : \"" + testQuestionsList + "\"");
		}

		sb.append(", \"order\" : \"" + String.valueOf(this.order) + "\"");
		sb.append("]");
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new LearningObject(this._id, 
				this.name, 
				this.description, 
				(ArrayList<Video>)this.videoList.clone(), 
				(ArrayList<Audio>)this.audioList.clone(), 
				(ArrayList<Document>)this.documentList.clone(),
				(ArrayList<TestQuestion>)this.testQuestionList.clone(), 
				this.order);
	}
}
