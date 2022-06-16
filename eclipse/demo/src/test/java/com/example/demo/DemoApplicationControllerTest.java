package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.controller.CourseController;
import com.example.demo.entity.Course;
import com.example.demo.entity.StudentRegistration;
import com.example.demo.dao.*;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class DemoApplicationControllerTest {
	
	@Autowired 
	StudentRegistrationDAO studentRegistrationDAO;
	
	@Autowired 
	CourseDAO courseDAO;
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CourseController courseController;
	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testCourseControllerIsNotNull() {
		Assertions.assertNotNull(courseController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	@WithMockUser(value = "admin") //test an mporei na dei th lista me ta ma8hmata
	@Test 
	void testListCoursesReturnsPage() throws Exception {
		mockMvc.perform(get("/courses/list")).
		andExpect(status().isOk()).
		andExpect(model().attributeExists("courses")). //mporei na mh to 8elei
		andExpect(view().name("Courses/list-courses"));
	}
	
	@WithMockUser(value = "admin") //test an bainei swsta ena ma8hma
	@Test 
	void testSaveCourseReturnsPage() throws Exception {
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getCourseId()));
	    multiValueMap.add("name", course.getName());
	    multiValueMap.add("login", course.getLogin());
	    multiValueMap.add("syllabus", course.getSyllabus());
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("projectPer", Double.toString(course.getProjectPer()));
	    multiValueMap.add("examsPer", Double.toString(course.getExamsPer()));
	    
		mockMvc.perform(
				post("/courses/save")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/courses/list"));	
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testCoursesAddPage() throws Exception { //test an fainetai h forma pros8hkhs ma8hmatos
		mockMvc.perform(get("/courses/showFormForAdd")).
		andExpect(status().isOk()).
		andExpect(model().attributeExists("course")). //mporei na mh to 8elei
		andExpect(view().name("Courses/course-form"));
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testCoursesUpdatePage() throws Exception { //test an fainetai h forma allaghs stoixeiwn ma8hmatos
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
		Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getCourseId()));
	    multiValueMap.add("name", course.getName());
	    multiValueMap.add("login", course.getLogin());
	    multiValueMap.add("syllabus", course.getSyllabus());
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("projectPer", Double.toString(course.getProjectPer()));
	    multiValueMap.add("examsPer", Double.toString(course.getExamsPer()));
		
	    mockMvc.perform(
				post("/courses/save")
			    .params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/courses/list"));
	   
	    courseDAO.flush();
	    
		mockMvc.perform(get("/courses/showFormForUpdate?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(status().isOk()).
		andExpect(model().attributeExists("course")). //mporei na mh to 8elei
		andExpect(view().name("Courses/course-form"));
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testCoursesUpdateValues() throws Exception { //test an allazoun ta stoixeia enos ma8hmatos
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAllInBatch();
		courseDAO.deleteAll();
		Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
		
		
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getCourseId()));
	    multiValueMap.add("name", course.getName());
	    multiValueMap.add("login", course.getLogin());
	    multiValueMap.add("syllabus", course.getSyllabus());
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("projectPer", Double.toString(course.getProjectPer()));
	    multiValueMap.add("examsPer", Double.toString(course.getExamsPer()));
		
	    mockMvc.perform(post("/courses/save").
	    params(multiValueMap)).
		andExpect(status().isFound()).
		andExpect(view().name("redirect:/courses/list"));
	    
	    courseDAO.flush();
		
	    mockMvc.perform(get("/courses/showFormForUpdate?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(status().isOk()).
		//andExpect(model().attributeExists("course")). //mporei na mh to 8elei
		andExpect(view().name("Courses/course-form"));
		
	    multiValueMap.remove("year");
	    multiValueMap.add("year", "3");
	    
		mockMvc.perform(post("/courses/save?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId())).
		params(multiValueMap)).
		andExpect(status().isFound()).
		andExpect(view().name("redirect:/courses/list"));
		
		courseDAO.flush();
		
		Course course2 = courseDAO.findById(courseDAO.findAll().get(0).getCourseId());
		Assertions.assertNotNull(course2);
		Assertions.assertEquals(3, course2.getYear());
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testCoursesDelete() throws Exception { //test an diagrafetai ena ma8hma
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getCourseId()));
	    multiValueMap.add("name", course.getName());
	    multiValueMap.add("login", course.getLogin());
	    multiValueMap.add("syllabus", course.getSyllabus());
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("projectPer", Double.toString(course.getProjectPer()));
	    multiValueMap.add("examsPer", Double.toString(course.getExamsPer()));
	    
		mockMvc.perform(
		post("/courses/save").
		params(multiValueMap)).
		andExpect(status().isFound()).
		andExpect(view().name("redirect:/courses/list"));
		
		mockMvc.perform(
		get("/courses/delete?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(view().name("redirect:/courses/list"));
		
		long num = courseDAO.count();
		Assertions.assertEquals(num, 0);
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testListStudentRegistrations() throws Exception { //test an mporei na dei to html me ta student registrations
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getCourseId()));
	    multiValueMap.add("name", course.getName());
	    multiValueMap.add("login", course.getLogin());
	    multiValueMap.add("syllabus", course.getSyllabus());
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("projectPer", Double.toString(course.getProjectPer()));
	    multiValueMap.add("examsPer", Double.toString(course.getExamsPer()));
	    
	    courseDAO.save(course);
	    
	    mockMvc.perform(get("/courses/list")).
		andExpect(status().isOk()).
		andExpect(view().name("Courses/list-courses"));
	    
	    mockMvc.perform(get("/courses/showStudentRegistrations?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(status().isOk()).
		andExpect(view().name("Courses/list-studentRegistrations"));
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testUpdateStudentRegistrations() throws Exception { //test an mporei na kanei update ena student registrations
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	    	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(course.getCourseId()));
	    multiValueMap.add("name", course.getName());
	    multiValueMap.add("login", course.getLogin());
	    multiValueMap.add("syllabus", course.getSyllabus());
	    multiValueMap.add("year", Integer.toString(course.getYear()));
	    multiValueMap.add("semester", Integer.toString(course.getSemester()));
	    multiValueMap.add("projectPer", Double.toString(course.getProjectPer()));
	    multiValueMap.add("examsPer", Double.toString(course.getExamsPer()));
	    
	    StudentRegistration studentRegistration = new StudentRegistration(1, "kwstas", 5, 9, 5, 5);
	    multiValueMap.add("studentId", Integer.toString(studentRegistration.getStudentId()));
	    multiValueMap.add("studentName", studentRegistration.getStudentName());
	    multiValueMap.add("yearOfStudies", Integer.toString(studentRegistration.getYearOfStudies()));
	    multiValueMap.add("studentSemester", Integer.toString(studentRegistration.getStudentSemester()));
	    multiValueMap.add("projectGrade", Double.toString(studentRegistration.getProjectGrade()));
	    multiValueMap.add("examsGrade", Double.toString(studentRegistration.getExamsGrade()));
	  
	    course.addStudentRegistration(studentRegistration);
	    courseDAO.save(course);
	    
	    mockMvc.perform(get("/courses/list")).
		andExpect(status().isOk()).
		andExpect(view().name("Courses/list-courses"));
	    
	    mockMvc.perform(get("/courses/showStudentRegistrations?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(status().isOk()).
		andExpect(view().name("Courses/list-studentRegistrations"));
	    
	    multiValueMap.remove("studentName");
	    multiValueMap.add("studentName", "mixalhs");
	    
	    mockMvc.perform(get("/courses/showFormForUpdate-studentRegistration?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId())+"&studentId="+Integer.toString(studentRegistrationDAO.findAll().get(0).getStudentId()))).
	    andExpect(status().isOk()).
		andExpect(view().name("Courses/studentRegistration-form"));  
	    
	    mockMvc.perform(post("/courses/save-studentRegistration?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId())+"&studentId="+Integer.toString(studentRegistrationDAO.findAll().get(0).getStudentId())).
		params(multiValueMap)).
	    andExpect(status().isFound()).
		andExpect(view().name("redirect:/courses/showStudentRegistrations?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId())));
	    
	    courseDAO.flush();
	    
	    StudentRegistration temp = courseDAO.findAll().get(0).getStudentRegistrations().get(0);
	    Assertions.assertNotNull(temp);
		Assertions.assertEquals("mixalhs", temp.getStudentName());
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testStudentRegistrationDelete() throws Exception { //test an diagrafetai ena student registration
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	   
	    StudentRegistration studentRegistration = new StudentRegistration(1, "kwstas", 5, 9, 5, 5);
	   
	    course.addStudentRegistration(studentRegistration);
	    courseDAO.save(course);
	    
	    mockMvc.perform(get("/courses/showStudentRegistrations?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(status().isOk()).
		andExpect(view().name("Courses/list-studentRegistrations"));
	    
	    mockMvc.perform(get("/courses/delete-studentRegistration?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId())+"&studentId="+Integer.toString(studentRegistrationDAO.findAll().get(0).getStudentId()))).
	    andExpect(view().name("redirect:/courses/showStudentRegistrations?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId())));
	    
	    //den xreiazetai edw flush() giati ginetai sto shmeio tou kwdika pou kaleitai
	    
	    long num = studentRegistrationDAO.count();
		Assertions.assertEquals(num, 0);
	}
	
	@WithMockUser(value = "admin")
	@Test 
	void testCalculateStatics() throws Exception { //test an fainotai kai ypologizontai swsta ta statistika twn foithtwn
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course(1,"Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5);
	    StudentRegistration studentRegistration1 = new StudentRegistration(1, "kwstas", 5, 9, 10, 10);
	    StudentRegistration studentRegistration2 = new StudentRegistration(2, "giorgos", 5, 9, 5, 5);
	    course.addStudentRegistration(studentRegistration1);
	    course.addStudentRegistration(studentRegistration2);
	    courseDAO.save(course);
	    
	    mockMvc.perform(get("/courses/calculateStatistics?courseId="+Integer.toString(courseDAO.findAll().get(0).getCourseId()))).
		andExpect(status().isOk()).
		andExpect(view().name("Courses/statisticsPrintout"));
	    
	    Course course1 = courseDAO.findAll().get(0);
	    Assertions.assertEquals(7.5, course1.getProjectAv()); //elegxos an einai swsto to project average
	    Assertions.assertEquals(7.5, course1.getExamsAv()); //elegxos an einai swsto to exams average
	    Assertions.assertEquals(7.5, course1.getFinalAv()); //elegxos an einai swsto to final average
	    Assertions.assertEquals(5, course1.getMin()); //elegxos an einai swsto to min
	    Assertions.assertEquals(10, course1.getMax()); //elegxos an einai swsto to max
	    Assertions.assertEquals(7.5, course1.getMean()); //elegxos an einai swsto to mean
	    Assertions.assertEquals(10, course1.getMedian()); //elegxos an einai swsto to median
	}
}
