package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Course;

@Repository
public interface CourseDAO extends JpaRepository<Course, Integer>{
	
	Course findByCourseId(int courseId);
	List<Course> findByLogin(String login);
	Course findById(int theId);
	
}
