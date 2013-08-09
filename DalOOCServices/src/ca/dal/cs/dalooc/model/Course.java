package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Course implements Serializable {

	private static final long serialVersionUID = 2952380125446310245L;

	@Id
	private String _id;
	
	private String name;
	
	private String description;
	
	private Syllabus syllabus;
	
	private ArrayList<LearningObject> learningObjectList;

	public Course() {
		super();
		this._id = new ObjectId().toString();
		this.learningObjectList = new ArrayList<LearningObject>();
	}
	
	public Course(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}

	public Course(String id, String name, String description) {
		this(name, description);
		this._id = id;
	}
	
	public Course(String id, String name, String description, ArrayList<LearningObject> learningObjectList) {
		this(name, description);
		this._id = id;
		this.learningObjectList = learningObjectList;
	}

	public Course(String id, String name, String description, ArrayList<LearningObject> learningObjectList, Syllabus syllabus) {
		this(id, name, description, learningObjectList);
		this.syllabus = syllabus;
	}

	public void setId(String id) {
		this._id = id;
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

	public Syllabus getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
	}

	public ArrayList<LearningObject> getLearningObjectList() {
		return learningObjectList;
	}

	public void setLearningObjectList(ArrayList<LearningObject> learningObjectList) {
		this.learningObjectList = learningObjectList;
	}

	public String getId() {
		return _id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof Course)) {
			return false;
		}
		
		return ((Course)o)._id.equals(this._id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this._id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
//		sb.append("\"Course\" [\"_id\" : \"" + this._id + "\"");
		sb.append("[\"_id\" : \"" + this._id + "\"");
		sb.append(", \"name\" : \"" + this.name + "\"");
		sb.append(", \"description\" : \"" + this.description + "\"");
		sb.append(", \"syllabus\" : \"" + this.syllabus);
		
		if (this.learningObjectList != null) {
			String learningObjectList = this.learningObjectList.toString();
			sb.append(", \"learningObjectList\" : \"" + learningObjectList + "\"");
		}
		
		sb.append("]");

		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Course(this._id, this.name, this.description, (ArrayList<LearningObject>)this.learningObjectList.clone(), (Syllabus)this.syllabus.clone());
	}
}
