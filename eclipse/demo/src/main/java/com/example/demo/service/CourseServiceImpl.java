package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CourseDAO;
import com.example.demo.dao.StudentRegistrationDAO;
import com.example.demo.entity.Course;
import com.example.demo.entity.StudentRegistration;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDAO courseRepository;
	@Autowired
	private StudentRegistrationDAO studentRegistrationRepository;
	
	public CourseServiceImpl() {
	}
	
	@Autowired
	public CourseServiceImpl(CourseDAO theCourseRepository, StudentRegistrationDAO theStudentRegistrationRepository) {
		courseRepository = theCourseRepository;
		studentRegistrationRepository = theStudentRegistrationRepository;
	}
	
	@Override
	@Transactional
	public List<Course> findAll(){
		return courseRepository.findAll();
	}
	
	@Override
	@Transactional
	public List<Course> findByLogin(String login) {
		List<Course> result = courseRepository.findByLogin(login);
		
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the course
			throw new RuntimeException("Did not any course for the instructor with id - " + login);
		}
	}
	
	@Override
	@Transactional
	public List<StudentRegistration> showRegistrations(int theCourseId){
		//System.out.println(findByCourseId(theCourseId).getName());
		//System.out.println(courseRepository.findByCourseId(theCourseId).getStudentRegistrations());
		return courseRepository.findByCourseId(theCourseId).getStudentRegistrations();
	}
	
	@Override
	@Transactional
	public Course findByCourseId(int theId) {
		Course result = courseRepository.findById(theId);
		
		if (result != null ) {
			return result;
		}
		else {
			// we didn't find the course
			throw new RuntimeException("Did not find course id - " + theId);
		}
	}

	@Override
	public void deleteStudentRegistration(int theCourseId, int theStudentId) {
		Course result = courseRepository.findById(theCourseId); 						// vriskw to course mesw tou id
		StudentRegistration x = studentRegistrationRepository.findById(theStudentId);	// vriskw to student registration mesw tou id
		result.deleteStudentRegistration(theStudentId); 								// diagrafw to student registration apo to arraylist sto course
		studentRegistrationRepository.delete(x); 										// kanw delete to student registration apo to vash
		studentRegistrationRepository.flush(); 											// pernaw tis allages twn antikeimenwn sth vash dedomenwn
		courseRepository.flush();
	}

	@Override
	public void saveStudentRegistration(int theCourseId, StudentRegistration theStudent) {	
		calculateStats(theCourseId);													// otan pame na prosthesoume ena mathima ksanaupologizoume tous bathmous logo twnn percentages
		studentRegistrationRepository.save(theStudent);
		courseRepository.flush();
		courseRepository.findByCourseId(theCourseId).addStudentRegistration(theStudent);
		courseRepository.flush();
	}
	
	@Override
	public void delete(int theCourseId) {
		courseRepository.deleteById(theCourseId);	
	}

	@Override
	public void save(Course theCourse) {
		courseRepository.save(theCourse);
	}
	
	@Override
	public List<Double> calculateStats(int theCourseId) {
		Course x = courseRepository.findByCourseId(theCourseId);
		List<Double> temp = x.getStats();
		courseRepository.save(x);
		return temp;
	}
}
