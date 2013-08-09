package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Syllabus implements Serializable {

	private static final long serialVersionUID = 4972782735596024948L;

	@Id
	private String id;
	
	private String instructor;
	
	private String courseDetail;
	
	private List<String> references;
	
	private List<String> prerequisites;

	public Syllabus() {
		super();
		this.id = new ObjectId().toString();
		this.references = new ArrayList<String>();
		this.prerequisites = new ArrayList<String>();
	}
	
	public Syllabus(String instructor, String courseDetail) {
		this();
		this.instructor = instructor;
		this.courseDetail = courseDetail;
	}
	public Syllabus(String id, String instructor, String courseDetail) {
		this(instructor, courseDetail);
		this.id = id;
	}

	public Syllabus(String id, String instructor, String courseDetail, ArrayList<String> references, ArrayList<String> prerequisites) {
		this(id, instructor, courseDetail);
		this.id = id;
		this.references = references;
		this.prerequisites = prerequisites;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getCourseDetail() {
		return courseDetail;
	}

	public void setCourseDetail(String courseDetail) {
		this.courseDetail = courseDetail;
	}

	public List<String> getReferences() {
		return references;
	}

	public void setReferences(List<String> references) {
		this.references = references;
	}

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Syllabus)) {
			return false;
		}
		return ((Syllabus)o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this.id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("[id : \"" + this.id + "\"");
		sb.append(", instructor : \"" + this.instructor + "\"");
		sb.append(", courseDetail : \"" + this.courseDetail + "\"");
		
		String referencesList = this.references.toString();
		sb.append(", references : " + referencesList);

		String prerequisitesList = this.prerequisites.toString();
		sb.append(", prerequisites : " + prerequisitesList);
		sb.append("]");

		return  sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Syllabus(this.id, 
				this.instructor, 
				this.courseDetail, 
				(ArrayList<String>)((ArrayList<String>)this.references).clone(), 
				(ArrayList<String>)((ArrayList<String>)this.prerequisites).clone());
	}
}
