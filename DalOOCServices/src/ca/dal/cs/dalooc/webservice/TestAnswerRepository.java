package ca.dal.cs.dalooc.webservice;

import ca.dal.cs.dalooc.model.TestAnswer;
import ca.dal.cs.dalooc.persistence.ApplicationContext;
import ca.dal.cs.dalooc.webservice.util.Parser;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class TestAnswerRepository {

	private ca.dal.cs.dalooc.persistence.TestAnswerRepository testAnswerRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.TestAnswerRepository.class);
	
	public String getTestAnswer(String userId, String courseId, String learningObjectId, String testQuestionId) {
		String[] names = { "userId", "courseId", "learningObjectId", "testQuestionId" };
		String[] values = { userId, courseId, learningObjectId, testQuestionId };
		
		TestAnswer user = this.testAnswerRepository.getObject(names , values );
		
		if (user != null) {
			return Parser.getTestAnswerDBObject(user).toString();
		}
		return "";
	}
	
	public void saveTestAnswer(String testAnswerString) {
		BasicDBObject testAnswerDBObject = (BasicDBObject)JSON.parse(testAnswerString);
		this.testAnswerRepository.saveObject(Parser.getTestAnswerObject(testAnswerDBObject));
	}
}
