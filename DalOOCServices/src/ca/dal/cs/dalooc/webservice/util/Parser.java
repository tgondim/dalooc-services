package ca.dal.cs.dalooc.webservice.util;

import ca.dal.cs.dalooc.model.Audio;
import ca.dal.cs.dalooc.model.Course;
import ca.dal.cs.dalooc.model.Document;
import ca.dal.cs.dalooc.model.LearningObject;
import ca.dal.cs.dalooc.model.Option;
import ca.dal.cs.dalooc.model.Syllabus;
import ca.dal.cs.dalooc.model.TestAnswer;
import ca.dal.cs.dalooc.model.TestQuestion;
import ca.dal.cs.dalooc.model.User;
import ca.dal.cs.dalooc.model.User.UserType;
import ca.dal.cs.dalooc.model.Video;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;



public class Parser {
	
	public static Course getCourseObject(BasicDBObject courseDBObject) {
		Syllabus syllabus;
		LearningObject learningObject;
//		Video video;
//		Audio audio;
//		Document document;
//		TestQuestion testQuestion;
//		Option option;
		BasicDBObject auxDBObject;
		BasicDBList auxDBList;
//		BasicDBList otherDBList;
//		BasicDBList anotherDBList;

		Course course = new Course();
		course.setId((String)courseDBObject.get("_id"));
		course.setName((String)courseDBObject.get("name"));
		course.setDescription((String)courseDBObject.get("description"));
		
		auxDBObject = (BasicDBObject)courseDBObject.get("syllabus");
		syllabus = new Syllabus();
		syllabus.setId(auxDBObject.getString("id"));
		syllabus.setInstructor(auxDBObject.getString("instructor"));
		syllabus.setCourseDetail(auxDBObject.getString("courseDetail"));
		
		auxDBList = (BasicDBList)auxDBObject.get("references");
		for (int k = 0; k < auxDBList.size(); k++) {
			syllabus.getReferences().add(auxDBList.get(k).toString());
		}

		auxDBList = (BasicDBList)auxDBObject.get("prerequesites");
		for (int k = 0; k < auxDBList.size(); k++) {
			syllabus.getPrerequisites().add(auxDBList.get(k).toString());
		}
		
		course.setSyllabus(syllabus);
		
		auxDBList = (BasicDBList)courseDBObject.get("learningObjectList");
		for (int k = 0; k < auxDBList.size(); k++) {
			learningObject = getLearningObject((BasicDBObject)auxDBList.get(k));
			
			course.getLearningObjectList().add(learningObject);
		}
		return course;
	}

	public static LearningObject getLearningObject(BasicDBObject learningObjectDBObject) {
		LearningObject learningObject;
		Video video;
		Audio audio;
		Document document;
		TestQuestion testQuestion;
		Option option;
		BasicDBList otherDBList;
		BasicDBList anotherDBList;
		learningObject = new LearningObject();
		learningObject.setId(learningObjectDBObject.getString("id"));
		learningObject.setName(learningObjectDBObject.getString("name"));
		learningObject.setDescription(learningObjectDBObject.getString("description"));
		
		otherDBList = (BasicDBList)learningObjectDBObject.get("videoList");
		for (int l = 0; l < otherDBList.size(); l++) {
			video = new Video();
			video.setId(((BasicDBObject)otherDBList.get(l)).getString("id"));
			video.setName(((BasicDBObject)otherDBList.get(l)).getString("name"));
			video.setDescription(((BasicDBObject)otherDBList.get(l)).getString("description"));
			video.setContentFileName(((BasicDBObject)otherDBList.get(l)).getString("contentFileName"));
			
			learningObject.getVideoList().add(video);
		}
		
		otherDBList = (BasicDBList)learningObjectDBObject.get("audioList");
		for (int l = 0; l < otherDBList.size(); l++) {
			audio = new Audio();
			audio.setId(((BasicDBObject)otherDBList.get(l)).getString("id"));
			audio.setName(((BasicDBObject)otherDBList.get(l)).getString("name"));
			audio.setDescription(((BasicDBObject)otherDBList.get(l)).getString("description"));
			audio.setContentFileName(((BasicDBObject)otherDBList.get(l)).getString("contentFileName"));
			
			learningObject.getAudioList().add(audio);
		}
		
		otherDBList = (BasicDBList)learningObjectDBObject.get("documentList");
		for (int l = 0; l < otherDBList.size(); l++) {
			document = new Document();
			document.setId(((BasicDBObject)otherDBList.get(l)).getString("id"));
			document.setName(((BasicDBObject)otherDBList.get(l)).getString("name"));
			document.setDescription(((BasicDBObject)otherDBList.get(l)).getString("description"));
			document.setContentFileName(((BasicDBObject)otherDBList.get(l)).getString("contentFileName"));
			
			learningObject.getDocumentList().add(document);
		}

		otherDBList = (BasicDBList)learningObjectDBObject.get("testQuestionList");
		for (int l = 0; l < otherDBList.size(); l++) {
			testQuestion = new TestQuestion();
			testQuestion.setId(((BasicDBObject)otherDBList.get(l)).getString("id"));
			testQuestion.setQuestion(((BasicDBObject)otherDBList.get(l)).getString("question"));
			testQuestion.setRelatedContendId(((BasicDBObject)otherDBList.get(l)).getString("relatedContendId"));
			
			anotherDBList = (BasicDBList)((BasicDBObject)otherDBList.get(l)).get("optionList");
			for (int m = 0; m < anotherDBList.size(); m++) {
				option = new Option();
				option.setId(((BasicDBObject)anotherDBList.get(m)).getString("id"));
				option.setItem(((BasicDBObject)anotherDBList.get(m)).getString("item"));
				option.setStatement(((BasicDBObject)anotherDBList.get(m)).getString("statement"));
				option.setCorrect(Boolean.valueOf(((BasicDBObject)anotherDBList.get(m)).getString("correct")));
				
				testQuestion.getOptionList().add(option);
			}
			
			learningObject.getTestQuestionList().add(testQuestion);
		}
		return learningObject;
	}
	
	public static BasicDBObject getCourseDBObject(Course course) {
		BasicDBObject courseDBObject;
		BasicDBObject syllabusDBObject;
		BasicDBObject learningObjectDBObject;
		BasicDBObject videoDBObject;
		BasicDBObject audioDBObject;
		BasicDBObject documentDBObject;
		BasicDBObject testQuestionDBObject;
		BasicDBObject optionDBObject;
		BasicDBList referencesDBList;
		BasicDBList prerequisitesDBList;
		BasicDBList learningObjectDBList;
		BasicDBList videoDBList;
		BasicDBList audioDBList;
		BasicDBList documentDBList;
		BasicDBList testQuestionDBList;
		BasicDBList optionDBList;
		courseDBObject = new BasicDBObject();
		courseDBObject.put("_id", course.getId());
		courseDBObject.put("name", course.getName());
		courseDBObject.put("description", course.getDescription());
		
		if (course.getSyllabus() != null) {
			syllabusDBObject = new BasicDBObject();
			syllabusDBObject.put("id", course.getSyllabus().getId());
			syllabusDBObject.put("instructor", course.getSyllabus().getInstructor());
			syllabusDBObject.put("courseDetail", course.getSyllabus().getCourseDetail());
			
			referencesDBList = new BasicDBList();
			for (String s : course.getSyllabus().getReferences()) {
				referencesDBList.add(s);
			}
			
			syllabusDBObject.put("references", referencesDBList);
			
			prerequisitesDBList = new BasicDBList();
			for (String s : course.getSyllabus().getPrerequisites()) {
				prerequisitesDBList.add(s);
			}
			
			syllabusDBObject.put("prerequesites", prerequisitesDBList);

			courseDBObject.put("syllabus", syllabusDBObject);
		}
		
		learningObjectDBList = new BasicDBList();
		for (LearningObject learningObject : course.getLearningObjectList())  {
			learningObjectDBObject = new BasicDBObject();
			learningObjectDBObject.put("id", learningObject.getId());
			learningObjectDBObject.put("name", learningObject.getName());
			learningObjectDBObject.put("description", learningObject.getDescription());
			
			videoDBList = new BasicDBList();
			for (Video video : learningObject.getVideoList()) {
				videoDBObject = new BasicDBObject();
				videoDBObject.put("id", video.getId());
				videoDBObject.put("name", video.getName());
				videoDBObject.put("description", video.getDescription());
				videoDBObject.put("contentFileName", video.getContentFileName());
				
				videoDBList.add(videoDBObject);
			}
			
			learningObjectDBObject.put("videoList", videoDBList);
			
			audioDBList = new BasicDBList();
			for (Audio audio : learningObject.getAudioList()) {
				audioDBObject = new BasicDBObject();
				audioDBObject.put("id", audio.getId());
				audioDBObject.put("name", audio.getName());
				audioDBObject.put("description", audio.getDescription());
				audioDBObject.put("contentFileName", audio.getContentFileName());
				
				audioDBList.add(audioDBObject);
			}
			
			learningObjectDBObject.put("audioList", audioDBList);

			documentDBList = new BasicDBList();
			for (Document document : learningObject.getDocumentList()) {
				documentDBObject = new BasicDBObject();
				documentDBObject.put("id", document.getId());
				documentDBObject.put("name", document.getName());
				documentDBObject.put("description", document.getDescription());
				documentDBObject.put("contentFileName", document.getContentFileName());
				
				documentDBList.add(documentDBObject);
			}
			
			learningObjectDBObject.put("documentList", documentDBList);

			testQuestionDBList = new BasicDBList();
			for (TestQuestion testQuestion : learningObject.getTestQuestionList()) {
				testQuestionDBObject = new BasicDBObject();
				testQuestionDBObject.put("id", testQuestion.getId());
				testQuestionDBObject.put("question", testQuestion.getQuestion());
				testQuestionDBObject.put("relatedContendId", testQuestion.getRelatedContendId());
				
				optionDBList = new BasicDBList();
				for (Option option : testQuestion.getOptionList()) {
					optionDBObject = new BasicDBObject();
					optionDBObject.put("id", option.getId());
					optionDBObject.put("item", option.getItem());
					optionDBObject.put("statement", option.getStatement());
					optionDBObject.put("correct", option.isCorrect());
					
					optionDBList.add(optionDBObject);
				}
				
				testQuestionDBObject.put("optionList", optionDBList);
				
				testQuestionDBList.add(testQuestionDBObject);
			}
			
			learningObjectDBObject.put("testQuestionList", testQuestionDBList);

			learningObjectDBList.add(learningObjectDBObject);
		}
		courseDBObject.put("learningObjectList", learningObjectDBList);
		
		return courseDBObject;
	}
	
	public static User getUserObject(BasicDBObject userDBObject) {
		User user = new User();
		user.setId((String)userDBObject.get("id"));
		user.setFirstName((String)userDBObject.get("firstName"));
		user.setLastName((String)userDBObject.get("lastName"));
		
		if (((String)userDBObject.get("userType")).equals("student")) {
			user.setUserType(UserType.STUDENT);
		} else if (((String)userDBObject.get("userType")).equals("professor")) {
			user.setUserType(UserType.PROFESSOR);
		}
		
		user.setEmail((String)userDBObject.get("email"));
		user.setPassword(((String)userDBObject.get("password")).toCharArray());
		user.setEmailValid(Boolean.valueOf((String)userDBObject.get("emailValid")));
		
		return user;
	}

	public static BasicDBObject getUserDBObject(User user) {
		BasicDBObject userDBObject;
		userDBObject = new BasicDBObject();
		userDBObject.put("id", user.getId());
		userDBObject.put("firstName", user.getFirstName());
		userDBObject.put("lastName", user.getLastName());
		userDBObject.put("userType", user.getUserType().getTypeName());
		userDBObject.put("email", user.getEmail());
		userDBObject.put("password", String.valueOf(user.getPassword()));
		userDBObject.put("emailValid", String.valueOf(user.isEmailValid()));
		
		return userDBObject;
	}

	public static BasicDBObject getTestAnswerDBObject(TestAnswer testAnswer) {
		BasicDBObject testAnswerDBObject;
		testAnswerDBObject = new BasicDBObject();
		testAnswerDBObject.put("id", testAnswer.getId());
		testAnswerDBObject.put("userId", testAnswer.getUserId());
		testAnswerDBObject.put("courseId", testAnswer.getCourseId());
		testAnswerDBObject.put("learningObjectId", testAnswer.getLearningObjectId());
		testAnswerDBObject.put("testQuestionId", testAnswer.getTestQuestionId());
		testAnswerDBObject.put("optionId", testAnswer.getOptionId());
		
		return testAnswerDBObject;
	}

	public static TestAnswer getTestAnswerObject(BasicDBObject testAnswerDBObject) {
		TestAnswer testAnswer = new TestAnswer();
		testAnswer.setId((String)testAnswerDBObject.get("id"));
		testAnswer.setUserId((String)testAnswerDBObject.get("userId"));
		testAnswer.setCourseId((String)testAnswerDBObject.get("courseId"));
		testAnswer.setLearningObjectId((String)testAnswerDBObject.get("learningObjectId"));
		testAnswer.setTestQuestionId((String)testAnswerDBObject.get("testQuestionId"));
		testAnswer.setOptionId((String)testAnswerDBObject.get("optionId"));
		
		return testAnswer;
	}
}
