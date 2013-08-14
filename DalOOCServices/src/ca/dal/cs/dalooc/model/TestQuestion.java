package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestQuestion implements Serializable {

	public enum Status {
		NONE,
		CORRECT,
		INCORRECT
	}

	private static final long serialVersionUID = -6169912738402228188L;

	@Id
	private String _id;
	
	private String question;
	
	private ArrayList<Option> optionList;
	
	private String relatedContendId;
	
	private int order;
	
	@Transient
	private Status status; 
	
	public TestQuestion() {
		super();
		this._id = new ObjectId().toString();
		this.optionList = new ArrayList<Option>();
		this.status = TestQuestion.Status.NONE;
	}
	
	public TestQuestion(String question) {
		this();
		this.question = question;
	}
	
	public TestQuestion(String id, String question) {
		this(question);
		this._id = id;
	}

	public TestQuestion(String id, String question, String relatedContendId) {
		this(id, question);
		this.relatedContendId = relatedContendId;
	}

	public TestQuestion(String question, ArrayList<Option> optionList, String relatedContendId) {
		this(question);
		this.optionList = optionList;
		this.relatedContendId = relatedContendId;
	}

	public TestQuestion(String id, String question, ArrayList<Option> options, String relatedContendId, int order) {
		this(question, options, relatedContendId);
		this._id = id;
		this.order = order;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(ArrayList<Option> options) {
		this.optionList = options;
	}

	public String getRelatedContendId() {
		return relatedContendId;
	}

	public void setRelatedContendId(String relatedContendId) {
		this.relatedContendId = relatedContendId;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Option getCorrectOption() {
		for (Option option : this.optionList) {
			if (option.isCorrect()) {
				return option;
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof TestQuestion)) {
			return false;
		}
		
		return ((TestQuestion)o)._id.equals(this._id);
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
		sb.append(", \"question\" : \"" + this.question + "\"");
		
		sb.append(", \"optionList\" : \"" + this.optionList + "\"");
		sb.append(", \"relatedContendId\" : \"" + this.relatedContendId + "\"");
		sb.append(", \"order\" : \"" + String.valueOf(this.order) + "\"");
		
		sb.append("]");

		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new TestQuestion(this._id, this.question, new ArrayList<Option>(this.optionList), this.relatedContendId, this.order);
	}
	
}
