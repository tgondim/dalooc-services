package ca.dal.cs.dalooc.webservice;

import java.util.List;

import ca.dal.cs.dalooc.model.TestAnswer;
import ca.dal.cs.dalooc.persistence.ApplicationContext;
import ca.dal.cs.dalooc.webservice.util.Parser;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class TestAnswerRepository {

	private ca.dal.cs.dalooc.persistence.TestAnswerRepository testAnswerRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.TestAnswerRepository.class);
	
	public String saveTestAnswer(String testAnswerString) {
		BasicDBObject testAnswerDBObject = (BasicDBObject)JSON.parse(testAnswerString);
		this.testAnswerRepository.saveObject(Parser.getTestAnswerObject(testAnswerDBObject));
		return "OK";
	}
	
	public String hasAnsweredCorrect(String userId, String courseId, String learningObjectId, String testQuestionId, String optionId) {		
		String[] names = { "userId", "courseId", "learningObjectId", "testQuestionId" };
		String[] values = { userId, courseId, learningObjectId, testQuestionId };
		
		List<TestAnswer> testAswerList = this.testAnswerRepository.getObjectList(names , values );
		
		for (TestAnswer testAswer : testAswerList) {
			if (testAswer.isCorrect()) {
				return "true";
			}
		}
		return "false";
	}
}
