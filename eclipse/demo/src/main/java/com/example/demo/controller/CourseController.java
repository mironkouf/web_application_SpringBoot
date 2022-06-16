package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Course;
import com.example.demo.entity.StudentRegistration;
import com.example.demo.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private CourseService courseService;
	
	public CourseController(CourseService theCourseService) {
		courseService = theCourseService;
	}
	
	// add mapping for "/list"
	@RequestMapping("/list")
	public String listCourses(Model theModel) {
		
		// get courses from db
		List<Course> theCourses = courseService.findAll();
		
		// add to the spring model
		theModel.addAttribute("courses", theCourses);
		
		return "Courses/list-courses";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Course theCourse = new Course();
		
		theModel.addAttribute("course", theCourse);
		
		return "Courses/course-form";
	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("courseId") int theId, Model theModel) {
			
		// get the course from the service
		Course theCourse = courseService.findByCourseId(theId);
		
		// set course as a model attribute to pre-populate the form
		theModel.addAttribute("course", theCourse);
		
		// send over to our form
		return "Courses/course-form";			
	}
	
	@RequestMapping("/showStudentRegistrations")
	public String showStudentRegistrations(@RequestParam("courseId") int theId, Model theModel) {		
		// get the course from the service
		List<StudentRegistration> theStudentRegistrations = courseService.showRegistrations(theId);
		courseService.calculateStats(theId);
		
		// set course as a model attribute to pre-populate the form
		theModel.addAttribute("studentRegistrations", theStudentRegistrations);
		
		// send over to our form
		return "Courses/list-studentRegistrations";			
	}
	
	@RequestMapping("/save")
	public String saveCourse(@ModelAttribute("courseId") Course theCourse) {
		
		// save the course
		courseService.save(theCourse);
		//otan kanw save ena course, to repository katalavainei oti paw na 3anavalw ena course me to idio id(afou auto den to peirazw)
		//kai diagrafei to palio kai pros8etei to kainourio course xwris na exw diplotypo
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/courses/list";
	}
	
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("courseId") int theId) {
		
		// delete the course
		courseService.delete(theId);
		
		// redirect to /courses/list
		return "redirect:/courses/list";
	}
	
	@RequestMapping("/showFormForUpdate-studentRegistration")
	public String showFormForUpdateStudentRegistration(@RequestParam("courseId") String theId, @RequestParam("studentId") int theStudentId, Model theModel) {
		
		// get the course from the service
		Course theCourse = courseService.findByCourseId(Integer.parseInt(theId));
		StudentRegistration theStudentRegistration = theCourse.getStudentRegistration(theStudentId);
		// set course as a model attribute to pre-populate the form
		theModel.addAttribute("studentRegistration", theStudentRegistration);
		
		// send over to our form
		return "Courses/studentRegistration-form";			
	}
	
	@RequestMapping("/delete-studentRegistration")
	public String deleteStudentRegistration(@RequestParam("courseId") String theId, @RequestParam("studentId") int theStudentId) {
		
		// delete the employee
		courseService.deleteStudentRegistration(Integer.parseInt(theId), theStudentId);
		// redirect to /employees/list
		return "redirect:/courses/showStudentRegistrations?courseId="+theId;
	}
	
	@RequestMapping("/save-studentRegistration")
	public String saveStudentRegistration(@RequestParam("courseId") String theId, @ModelAttribute("studentId") StudentRegistration theStudentRegistration) {
		
		//System.out.println("Id == " + theStudentRegistration.getStudentId());	//debugging tool
		courseService.saveStudentRegistration(Integer.parseInt(theId), theStudentRegistration);
		
		return "redirect:/courses/showStudentRegistrations?courseId="+theId;
	}
	
	@RequestMapping("/showFormForAdd-StudentRegistration")
	public String showFormForAddStudentRegistration(@RequestParam("courseId") String theId, Model theModel) {
		
		// create model attribute to bind form data
		StudentRegistration theStudent = new StudentRegistration();
		
		theModel.addAttribute("studentRegistration", theStudent);
		
		return "Courses/studentRegistration-form";
	}
	
	@RequestMapping("/calculateStatistics")
	public String calculateStatistics(@RequestParam("courseId") String theId, Model theModel) {
		// h seira me thn opoia mpainoun ta statistika mesa sth lista mas(statistics)
		//projectgrade
		//examsgrade
		//finalgrade
		//min
		//max
		//mean
		//median
		List<Double> statistics = courseService.calculateStats(Integer.parseInt(theId));
		
		theModel.addAttribute("statistics", statistics);
		return "Courses/statisticsPrintout";
	}
}
