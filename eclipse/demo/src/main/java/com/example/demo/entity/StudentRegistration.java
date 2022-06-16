package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student_registration")
public class StudentRegistration {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="student_id")
	private int studentId;
	
	@Column(name="student_name")
	private String studentName;
	
	@Column(name="year_of_studies")
	private int yearOfStudies;
	
	@Column(name="student_semester")
	private int studentSemester;
	
	@Column(name="project_grade")
	private double projectGrade;
	
	@Column(name="exams_grade")
	private double examsGrade;
	
	@Column(name="final_grade")
	private double finalGrade;

	
	public StudentRegistration() {
		super();
	}
	
	public StudentRegistration(int studentId, String studentName, int yearOfStudies, int studentSemester, double projectGrade, double examsGrade) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.yearOfStudies = yearOfStudies;
		this.studentSemester = studentSemester;
		this.projectGrade = projectGrade;
		this.examsGrade = examsGrade;
		this.finalGrade = 0.5*projectGrade + 0.5*examsGrade;	
		// valame san percentages ta default(omws o swstos upologismos tou final grade tha ginei sto courseServiseImpl)
	}

	public StudentRegistration(String studentName, int yearOfStudies, int studentSemester, double projectGrade, double examsGrade) {
		this.studentName = studentName;
		this.yearOfStudies = yearOfStudies;
		this.studentSemester = studentSemester;
		this.projectGrade = projectGrade;
		this.examsGrade = examsGrade;
		this.finalGrade = 0.5*projectGrade + 0.5*examsGrade;	
		// valame san percentages ta default(omws o swstos upologismos tou final grade tha ginei sto courseServiseImpl)
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getYearOfStudies() {
		return yearOfStudies;
	}

	public void setYearOfStudies(int yearOfStudies) {
		this.yearOfStudies = yearOfStudies;
	}

	public int getStudentSemester() {
		return studentSemester;
	}

	public void setStudentSemester(int studentSemester) {
		this.studentSemester = studentSemester;
	}

	public double getProjectGrade() {
		return projectGrade;
	}

	public void setProjectGrade(double projectGrade) {
		this.projectGrade = projectGrade;
	}

	public double getExamsGrade() {
		return examsGrade;
	}

	public void setExamsGrade(double examsGrade) {
		this.examsGrade = examsGrade;
	}

	public double getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}

	@Override
	public String toString() {
		return "StudentRegistration [studentId=" + studentId + ", studentName=" + studentName + ", yearOfStudies="
				+ yearOfStudies + ", studentSemester=" + studentSemester + ", projectGrade=" + projectGrade
				+ ", examsGrade=" + examsGrade + ", finalGrade=" + finalGrade + "]";
	}
}
