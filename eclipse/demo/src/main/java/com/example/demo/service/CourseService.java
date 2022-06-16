package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Course;
import com.example.demo.entity.StudentRegistration;

public interface CourseService {
	
	public List<Course> findAll();
	public List<Course> findByLogin(String theCourseId);
	public List<StudentRegistration> showRegistrations(int theCourseId);
	public Course findByCourseId(int theId);
	public void deleteStudentRegistration(int theCourseId, int studentId);
	public void saveStudentRegistration(int theCourseId, StudentRegistration theStudent);
	public void delete(int theCourseId);
	public void save(Course theCourse);
	public List<Double> calculateStats(int theCourseId);
}
