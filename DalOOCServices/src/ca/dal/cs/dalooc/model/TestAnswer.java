package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestAnswer implements Serializable {
	
	private static final long serialVersionUID = 2705882188259380281L;

	@Id
	private String _id;

	private String userId;

	private String courseId;
	
	private String learningObjectId;
	
	private String testQuestionId;

	private String optionId;
	
	private boolean correct;

	public TestAnswer() {
		super();
		this._id = new ObjectId().toString();
	}

	public TestAnswer(String userId, String courseId, String learningObjectId,
			String testQuestionId, String optionId) {
		this();
		this.userId = userId;
		this.courseId = courseId;
		this.learningObjectId = learningObjectId;
		this.testQuestionId = testQuestionId;
		this.optionId = optionId;
	}

	public TestAnswer(String id, String userId, String courseId, String learningObjectId,
			String testQuestionId, String optionId) {
		this(userId, courseId, learningObjectId, testQuestionId, optionId);
		this._id = id;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getLearningObjectId() {
		return learningObjectId;
	}

	public void setLearningObjectId(String learningObjectId) {
		this.learningObjectId = learningObjectId;
	}

	public String getTestQuestionId() {
		return testQuestionId;
	}

	public void setTestQuestionId(String testQuestionId) {
		this.testQuestionId = testQuestionId;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	
	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof TestAnswer)) {
			return false;
		}
		
		return ((TestAnswer)o)._id.equals(this._id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this._id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
//		sb.append("\"TestAnswer\" [\"id\" : \"" + this.id + "\"");
		sb.append("[\"_id\" : \"" + this._id + "\"");
		sb.append(", \"userId\" : \"" + this.userId + "\"");
		sb.append(", \"courseId\" : \"" + this.courseId + "\"");
		sb.append(", \"learningObjectId\" : \"" + this.learningObjectId + "\"");
		sb.append(", \"testQuestionId\" : \"" + this.testQuestionId + "\"");
		sb.append(", \"optionId\" : \"" + this.optionId + "\"");
		sb.append(", \"correct\" : \"" + String.valueOf(this.correct) + "\"");
		
		sb.append("]");

		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new TestAnswer(this._id, this.userId, this.courseId, this.learningObjectId, this.testQuestionId, this.optionId);
	}
}
