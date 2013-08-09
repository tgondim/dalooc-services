package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Video implements Serializable, LearningObjectContent {

	private static final long serialVersionUID = -7467666512127374196L;

	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	private String contentFileName;
	
	public Video() {
		super();
		this.id = new ObjectId().toString();
	}

	public Video(String name, String description, String contentFileName) {
		this();
		this.name = name;
		this.description = description;
		this.contentFileName = contentFileName;
	}

	public Video(String id, String name, String description, String contentFileName) {
		this(name, description, contentFileName);
		this.id = id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
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
		if (!(o instanceof Video)) {
			return false;
		}
		return ((Video)o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this.id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
//		sb.append("\"Video\" [\"id\" : \"" + this.id + "\"");
		sb.append("[\"id\" : \"" + this.id + "\"");
		sb.append(", \"name\" : \"" + this.name + "\"");
		sb.append(", \"description\" : \"" + this.description + "\"");
		sb.append(", \"contentFileName\" : \"" + this.contentFileName + "\"");
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Video(this.id, this.name, this.description, this.contentFileName);
	}
}
