package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestQuestion implements Serializable {

	private static final long serialVersionUID = -6169912738402228188L;

	@Id
	private String id;
	
	private String question;
	
	private ArrayList<Option> optionList;
	
	private String relatedContendId;
	
	public TestQuestion() {
		super();
		this.id = new ObjectId().toString();
		this.optionList = new ArrayList<Option>();
	}
	
	public TestQuestion(String question) {
		this();
		this.question = question;
	}
	
	public TestQuestion(String id, String question) {
		this(question);
		this.id = id;
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

	public TestQuestion(String id, String question, ArrayList<Option> options, String relatedContendId) {
		this(question, options, relatedContendId);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		
		return ((TestQuestion)o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this.id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
//		sb.append("\"TestQuestion\" [\"id\" : \"" + this.id + "\"");
		sb.append("[\"id\" : \"" + this.id + "\"");
		sb.append(", \"question\" : \"" + this.question + "\"");
		
		sb.append(", \"optionList\" : \"" + this.optionList + "\"");
		sb.append(", \"relatedContendId\" : \"" + this.relatedContendId + "\"");
		
		sb.append("]");

		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new TestQuestion(this.id, this.question, new ArrayList<Option>(this.optionList), this.relatedContendId);
	}
	
}
