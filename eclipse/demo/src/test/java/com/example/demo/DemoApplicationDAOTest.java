package com.example.demo;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.entity.Course;
import com.example.demo.dao.*;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class DemoApplicationDAOTest {
	
	@Autowired 
	StudentRegistrationDAO studentRegistrationDAO;
	
	@Autowired 
	CourseDAO courseDAO;
	
	@WithMockUser(value = "admin") //test an bainei swsta ena ma8hma kai na to vrei me to id
	@Test 
	void testSaveCourseToDAOFindById() throws Exception {
		studentRegistrationDAO.deleteAll();
		courseDAO.deleteAll();
	    Course course = new Course("Xhmeia","kathigitis 1","organikh",2,3,0.5,0.5); //to vazw xwris id gia na to parei mono tou
	    
	    courseDAO.save(course);
	    
	    Assertions.assertNotNull(courseDAO.findById(course.getCourseId()));
	}
	
}
