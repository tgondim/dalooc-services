package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Option implements Serializable {
	
	private static final long serialVersionUID = -1635574802269444795L;

	@Id
	private String _id;
	
	private String item;
	
	private String statement;
	
	private boolean correct;

	public Option() {
		super();
		this._id = new ObjectId().toString();
	}

	public Option(String item, String statement, boolean correct) {
		this();
		this.item = item;
		this.statement = statement;
		this.correct = correct;
	}

	public Option(String id, String item, String statement, boolean correct) {
		this(item, statement, correct);
		this._id = id;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
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
		
		if (!(o instanceof Option)) {
			return false;
		}
		
		return ((Option)o)._id.equals(this._id);
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
		sb.append(", \"item\" : \"" + this.item + "\"");
		sb.append(", \"statement\" : \"" + this.statement + "\"");
		
		sb.append(", \"correct\" : \"" + this.correct + "\"");
		
		sb.append("]");

		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Option(this._id, this.statement, this.correct);
	}

}
