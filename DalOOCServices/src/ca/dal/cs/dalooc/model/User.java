package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User implements Serializable {

	private static final long serialVersionUID = -78721853166922769L;
	
	public enum UserType implements Serializable {
		STUDENT("student"),
		PROFESSOR("professor");
		
		private String typeName;
		
		private UserType(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			return typeName;
		}
	}
	
	@Id
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private UserType userType;
	
	private String email;
	
	private char[] password;

	private boolean emailValid;
	
	public User() {
		super();
		this.id = new ObjectId().toString();
	}
	
	public User(String firstName, String lastName, UserType userType,
			String email, char[] password, boolean emailValid) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.email = email;
		this.password = password;
		this.emailValid = emailValid;
	}

	public User(String id, String firstName, String lastName,
			UserType userType, String email, char[] password, boolean emailValid) {
		this(firstName, lastName, userType, email, password, emailValid);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public boolean isEmailValid() {
		return emailValid;
	}

	public void setEmailValid(boolean emailValid) {
		this.emailValid = emailValid;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof User)) {
			return false;
		}
		return ((User)o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this.id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
//		sb.append("\"User\" [\"id\" : \"" + this.id + "\""); 
		sb.append("[\"id\" : \"" + this.id + "\""); 
		sb.append(", \"firstName\" : \"" + this.firstName + "\""); 
		sb.append(", \"lastName\" : \"" + this.lastName + "\"");
		sb.append(", \"userType\" : \"" + this.userType.typeName + "\"");
		sb.append(", \"login\" : \"" + this.email + "\"");
		sb.append(", \"password\" : \"" + String.valueOf(this.password) + "\"]");
		sb.append(", \"emailValid\" : \"" + String.valueOf(this.emailValid) + "\"]");
		
		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new User(this.id, this.firstName, this.lastName, this.userType, this.email, this.password, this.emailValid);
	}
	
}
