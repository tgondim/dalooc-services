package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document
public class LearningObject implements Serializable {
	
	private static final long serialVersionUID = -7926340467663710908L;

	@Id
	private String id;

	private String name;
	
	private String description;

	private ArrayList<Video> videoList;
	
	private ArrayList<Audio> audioList;
	
	private ArrayList<Document> documentList;

	private ArrayList<TestQuestion> testQuestionList;

	public LearningObject() {
		super();
		this.id = new ObjectId().toString();
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
		this.id = id;
	}
	
	public LearningObject(String id, 
			String name, 
			String description, 
			ArrayList<Video> videoList, 
			ArrayList<Audio> audioList, 
			ArrayList<Document> documentList,
			ArrayList<TestQuestion> testQuestionsList) {
		this(id, name, description);
		this.videoList = videoList;
		this.audioList = audioList;
		this.documentList = documentList;
		this.testQuestionList = testQuestionsList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
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

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof LearningObject)) {
			return false;
		}
		return ((LearningObject)o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this.id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

//		sb.append("\"LearningObject\" [\"id\" : \"" + this.id + "\"");
		sb.append("[\"id\" : \"" + this.id + "\"");
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
		sb.append("]");
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new LearningObject(this.id, 
				this.name, 
				this.description, 
				(ArrayList<Video>)this.videoList.clone(), 
				(ArrayList<Audio>)this.audioList.clone(), 
				(ArrayList<Document>)this.documentList.clone(),
				(ArrayList<TestQuestion>)this.testQuestionList.clone());
	}
}
