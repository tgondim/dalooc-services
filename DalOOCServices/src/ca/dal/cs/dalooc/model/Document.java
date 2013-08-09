package ca.dal.cs.dalooc.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document
public class Document implements Serializable, LearningObjectContent {
	
	private static final long serialVersionUID = 2299573581005816207L;

	@Id
	private String id;

	private String name;
	
	private String description;
	
	private String contentFileName;
	
	public enum DocumentType {
		UNKNOWN,
		PDF,
		DOC,
		PPT,
		XSL,
		JPG
	}
	
	private DocumentType type;
	
	public Document() {
		super();
		this.id = new ObjectId().toString();
		this.type = DocumentType.UNKNOWN;
	}
	
	public Document(String name, String description, String contentFileName) {
		this();
		this.name = name;
		this.description = description;
		this.contentFileName = contentFileName;
	}

	public Document(String name, String description, String contentFileName, DocumentType type) {
		this(name, description, contentFileName);
		this.type = type;
	}

	public Document(String id, String name, String description, String contentFileName) {
		this(name, description, contentFileName);
		this.id = id;
	}

	public Document(String id, String name, String description, String contentFileName, DocumentType type) {
		this(name, description, contentFileName, type);
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
	
	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}
	
	public static DocumentType getDocumentType(String extension) {
		if (extension.equals("pdf")) {
			return DocumentType.PDF;
		}
		if (extension.equals("doc")) {
			return DocumentType.DOC;
		}
		if (extension.equals("ppt")) {
			return DocumentType.PPT;
		}
		if (extension.equals("xsl")) {
			return DocumentType.XSL;
		}
		if (extension.equals("jpg")) {
			return DocumentType.JPG;
		}
		return DocumentType.UNKNOWN;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Document)) {
			return false;
		}
		return ((Document)o).id.equals(this.id);
	}
	
	@Override
	public int hashCode() {
		BigInteger big = new BigInteger(this.id, 16);
		return super.hashCode() * (big.intValue() + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
//		sb.append("\"Document\" [\"id\" : \"" + this.id + "\"");
		sb.append("[\"id\" : \"" + this.id + "\"");
		sb.append(", \"name\" : \"" + this.name + "\"");
		sb.append(", \"description\" : \"" + this.description + "\"");
		sb.append(", \"contentFileName\" : \"" + this.contentFileName + "\"");
		sb.append("]");
		
		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Document(this.id, this.name, this.description, this.contentFileName, this.type);
	}
	
}
