package ca.dal.cs.dalooc.webservice;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.dal.cs.dalooc.model.Course;
import ca.dal.cs.dalooc.persistence.ApplicationContext;
import ca.dal.cs.dalooc.webservice.util.Parser;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class CourseRepository {

	private ca.dal.cs.dalooc.persistence.CourseRepository courseRepository = ApplicationContext.getInstance().getBean(ca.dal.cs.dalooc.persistence.CourseRepository.class);
	
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
		
		return "OK";
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
		
		return result.toString();
	}
	
	public String removeCourse(String courseId) {
		this.courseRepository.deleteObject("_id", courseId);
		
		return "true";
	}
}
