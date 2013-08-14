package ca.dal.cs.dalooc.webservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.Course;
import ca.dal.cs.dalooc.model.TestAnswer;
import ca.dal.cs.dalooc.model.TestQuestion;
import ca.dal.cs.dalooc.persistence.ApplicationContext;
import ca.dal.cs.dalooc.webservice.util.Parser;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class CourseRepository {

	private ca.dal.cs.dalooc.persistence.CourseRepository courseRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.CourseRepository.class);
	
	private ca.dal.cs.dalooc.persistence.TestAnswerRepository testAnswerRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.TestAnswerRepository.class);
	
	public String getAllCourses() {
		
		ArrayList<Course> courseList = (ArrayList<Course>)this.courseRepository.getAllObjects();
		
		if (courseList != null) {
			
			BasicDBList courseDBList = new BasicDBList();
			for (Course course : courseList) {
				courseDBList.add(Parser.getCourseDBObject(course));
			}
			return courseDBList.toString();
		}
		return null;
	}

	public String saveCourse(String courseString) {
		BasicDBObject courseDBObject = (BasicDBObject)JSON.parse(courseString);
		Course course = Parser.getCourseObject(courseDBObject);
		this.courseRepository.saveObject(course);
		
		return "true";
	}
	
	public String updateCourse(String courseId, String courseString) {
		BasicDBObject courseDBObject = (BasicDBObject)JSON.parse(courseString);
		Course courseToDB = Parser.getCourseObject(courseDBObject);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(courseToDB.getId()));
		
		Update update = new Update();
		update.set("name", courseToDB.getName());
		update.set("description", courseToDB.getDescription());
		update.set("learningObjectList", courseToDB.getLearningObjectList());
		update.set("syllabus", courseToDB.getSyllabus());
		
		WriteResult result = this.courseRepository.updateObject(query, update);
		
		if (result.getLastError().get("err") != null) {
			return "false";
		}
		
		return "true";
	}
	
	public String removeCourse(String courseId) {
		this.courseRepository.deleteObject("_id", courseId);
		
		return "true";
	}
	
	public String getLearningObjectStatus(String userId, String courseId, String learningObjectIndex) {
		String returnString = "todo";
		int foundCorrect = 0;
		
		Course course = this.courseRepository.getObject(new String[] { "_id" }, new String[] { courseId } );
		int index = Integer.valueOf(learningObjectIndex);
		
		String[] names = { "userId", "courseId", "learningObjectId", "testQuestionId" };
		String[] values = new String[4];
		
		values[0] = userId;
		values[1] = courseId;
		values[2] = course.getLearningObjectList().get(index).getId();
		
		List<TestAnswer> testAswerList;
		
		for (TestQuestion testQuestion : course.getLearningObjectList().get(index).getTestQuestionList()) {
			values[3] = testQuestion.getId();

			testAswerList = this.testAnswerRepository.getObjectList(names , values);
			
			if (testAswerList.size() > 0) {
				for (TestAnswer testAswer : testAswerList) {
					if (testAswer.isCorrect()) {
						foundCorrect++;
						break;
					}
				}
			}
		}
		
		int numberOfQuestions = course.getLearningObjectList().get(index).getTestQuestionList().size();
		if (foundCorrect == 0) {
			returnString = "0";
		} else {
			returnString = String.valueOf(foundCorrect/(double)numberOfQuestions);
		}

		return returnString;
	}
}
