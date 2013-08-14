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
	private String _id;
	
	private String name;
	
	private String description;
	
	private String contentFileName;
	
	private int order;
	
	public Video() {
		super();
		this._id = new ObjectId().toString();
	}

	public Video(String name, String description, String contentFileName) {
		this();
		this.name = name;
		this.description = description;
		this.contentFileName = contentFileName;
	}

	public Video(String id, String name, String description, String contentFileName, int order) {
		this(name, description, contentFileName);
		this._id = id;
		this.order = order;
	}

	@Override
	public void setId(String id) {
		this._id = id;
	}

	@Override
	public String getId() {
		return _id;
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
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Video)) {
			return false;
		}
		return ((Video)o)._id.equals(this._id);
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
		sb.append(", \"contentFileName\" : \"" + this.contentFileName + "\"");
		sb.append(", \"order\" : \"" + this.order + "\"");
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Video(this._id, this.name, this.description, this.contentFileName, this.order);
	}
}
