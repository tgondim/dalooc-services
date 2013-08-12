package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Audio implements Serializable, LearningObjectContent {

	private static final long serialVersionUID = -2401466755865587517L;

	@Id
	private String _id;

	private String name;

	private String description;
	
	private String contentFileName;
	
	public Audio() {
		super();
		this._id = new ObjectId().toString();
	}
	
	public Audio(String name, String description, String contentFileName) {
		this();
		this.setName(name);
		this.setDescription(description);
		this.setContentFileName(contentFileName);
	}

	public Audio(String id, String name, String description, String contentFileName) {
		this(name, description, contentFileName);
		this.setId(id);
	}

	@Override
	public String getId() {
		return _id;
	}
	
	@Override
	public void setId(String id) {
		this._id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getContentFileName() {
		return contentFileName;
	}

	@Override
	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Audio)) {
			return false;
		}
		return ((Audio)o)._id.equals(this._id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this._id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
//		sb.append("\"Audio\" [\"id\" : \"" + this.id + "\""); 
		sb.append("[\"_id\" : \"" + this._id + "\""); 
		sb.append(", \"name\" : \"" + this.name + "\""); 
		sb.append(", \"description\" : \"" + this.description + "\"");
		sb.append(", \"contentFileName\" : \"" + this.contentFileName + "]");
		
		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Audio(this._id, this.name, this.description, this.contentFileName);
	}
	
}
