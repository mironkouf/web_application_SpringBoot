package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="course_id")
	private int courseId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="login")
	private String login;
	
	@Column(name="syllabus")
	private String syllabus;
	
	@Column(name="year")
	private int year;
	
	@Column(name="semester")
	private int semester;
	
	@Column(name="projectper")
	private double projectPer;
	
	@Column(name="examsper")
	private double examsPer;
	
	@Column(name="projectav")
	private double projectAv;
	
	@Column(name="examsav")
	private double examsAv;
	
	@Column(name="finalav")
	private double finalAv;
	
	@Column(name="min")
	private double min;
	
	@Column(name="max")
	private double max;
	
	@Column(name="mean")
	private double mean;
	
	@Column(name="median")
	private double median;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = StudentRegistration.class, cascade=CascadeType.ALL)
	@JoinColumn(name="course_id1")
	private List<StudentRegistration> studentRegistrations;
	
	public Course() {
		super();
		this.studentRegistrations = new ArrayList<StudentRegistration>();
	}
	
	public Course(int courseId, String name, String login, String syllabus, int year, int semester, double projectPer, double examsPer) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.login = login;
		this.syllabus = syllabus;
		this.year = year;
		this.semester = semester;
		this.projectPer = projectPer;
		this.examsPer = examsPer;
		this.studentRegistrations = new ArrayList<StudentRegistration>();
	}
	
	public Course(String name, String login, String syllabus, int year, int semester, double projectPer, double examsPer) {
		super();
		this.name = name;
		this.login = login;
		this.syllabus = syllabus;
		this.year = year;
		this.semester = semester;
		this.projectPer = projectPer;
		this.examsPer = examsPer;
		this.studentRegistrations = new ArrayList<StudentRegistration>();
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}
	
	public double getExamsPer() {
		return examsPer;
	}

	public void setExamsPer(double examsPer) {
		this.examsPer = examsPer;
		calculateFinalGrade();	
		// kathe fora pou allazei ena baros na ksana upologizoume ton teliko bathmo gia kathe studentRegistration sto sugkekrimeno mathima
	}
	
	public double getProjectPer() {
		return projectPer;
	}

	public void setProjectPer(double projectPer) {
		this.projectPer = projectPer;
		calculateFinalGrade();
		// kathe fora pou allazei ena baros na ksana upologizoume ton teliko bathmo gia kathe studentRegistration sto sugkekrimeno mathima
	}

	public double getProjectAv() {
		return projectAv;
	}

	public void setProjectAv(double projectAv) {
		this.projectAv = projectAv;
	}

	public double getExamsAv() {
		return examsAv;
	}

	public void setExamsAv(double examsAv) {
		this.examsAv = examsAv;
	}

	public double getFinalAv() {
		return finalAv;
	}

	public void setFinalAv(double finalAv) {
		this.finalAv = finalAv;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public List<StudentRegistration> getStudentRegistrations() {
		return studentRegistrations;
	}

	public void setStudentRegistrations(List<StudentRegistration> studentRegistrations) {
		this.studentRegistrations = studentRegistrations;
	}
	
	public void addStudentRegistration(StudentRegistration studentRegistration) {
		studentRegistrations.add(studentRegistration);
	}
	
	public void deleteStudentRegistration(int id) {
		for(int i=0; i<studentRegistrations.size(); i++) {
			if(id == studentRegistrations.get(i).getStudentId()) {
				studentRegistrations.remove(i);
				break;
			}
		}
	}
	
	public StudentRegistration getStudentRegistration(int id) {
		for(int i=0; i<studentRegistrations.size(); i++) {
			if(id == studentRegistrations.get(i).getStudentId()) {
				return studentRegistrations.get(i);
			}
		}
		return null;
	}
	
	// upologizontai oi mesoi oroi gia project,exams,finals
	// kai kratame ton elaxisto, ton megisto, ton mesaio bathmo kai to meso oro twn telikwn bathmwn 
	public ArrayList<Double> getStats(){
		calculateFinalGrade();
		ArrayList<Double> finalResults = new ArrayList<Double>();	// h lista pou krataei ta telika apotelesmata twn statistikwn
		if(studentRegistrations.size() != 0) {
			double avgProject = 0;
			double avgExams = 0;
			double avgFinal = 0;
			double tempMean = 0;
			ArrayList<Double> finalGrades = new ArrayList<Double>();			// h lista pou krataei olous tous telikous(finalGrade) bathmous
			for(int i=0; i<studentRegistrations.size(); i++) {
				avgProject += studentRegistrations.get(i).getProjectGrade();	// kratame to athroisma twn project
				avgExams += studentRegistrations.get(i).getExamsGrade();		// kratame to athroisma twn exams
				avgFinal += studentRegistrations.get(i).getFinalGrade();		// kratame to athroisma twn final grades 
				tempMean += studentRegistrations.get(i).getFinalGrade();		// xrhsimopoeitai gia dieukolunsh mas (den diaferei apo to avgFinal)
				finalGrades.add(studentRegistrations.get(i).getFinalGrade());	// bazoume olous tous telikous bathmous sth lista gia ton upologismo twn statistikwn
			}		
			tempMean = tempMean / studentRegistrations.size();
			Collections.sort(finalGrades);										// sortaroume gia na paroume min,max,median
			int tempMedian = finalGrades.size()/2;								// position gia ton mesaio (an zugos kratame panw akeraio meros)
			avgProject = avgProject / studentRegistrations.size();				// upologizoume ton meso oro 
			avgExams = avgExams / studentRegistrations.size();					// upologizoume ton meso oro 
			avgFinal = avgFinal / studentRegistrations.size();					// upologizoume ton meso oro 
			DecimalFormat df = new DecimalFormat("#.##");						// xrhsimopoieitai gia na kratame mono duo pshfia meta thn upodiastolh stous double arithmous
			finalResults.add(Double.parseDouble(df.format(avgProject)));
			finalResults.add(Double.parseDouble(df.format(avgExams)));
			finalResults.add(Double.parseDouble(df.format(avgFinal)));
			finalResults.add(finalGrades.get(0));
			finalResults.add(finalGrades.get(finalGrades.size()-1));
			finalResults.add(Double.parseDouble(df.format(tempMean)));
			finalResults.add(finalGrades.get(tempMedian));		
			projectAv = Double.parseDouble(df.format(avgProject));
			examsAv = Double.parseDouble(df.format(avgExams));
			finalAv = Double.parseDouble(df.format(avgFinal));
			min = finalGrades.get(0);
			max = finalGrades.get(finalGrades.size()-1);
			mean = tempMean;
			median = finalGrades.get(tempMedian);
		}
		return finalResults;	// epistrefoume thn telikh lista gia na thn xrhsimopoihsei h html
	}
	
	public void calculateFinalGrade() { // san default timh(sta hdh uparxonta mathimata) dinoume 50% varythta stous va8mous e3etashs kai project gia na vgei o telikos va8mos
		if(studentRegistrations.size() != 0) {
			for(int i=0; i<studentRegistrations.size(); i++) {
				StudentRegistration temp = studentRegistrations.get(i);
				double tempFinal = temp.getExamsGrade() * examsPer + temp.getProjectGrade() * projectPer;
				temp.setFinalGrade(tempFinal);
			}
		}
	}

	@Override
	public String toString() {
		return "Course [courseId = " + courseId + ", name = " + name + ", login = " + login + ", syllabus = " + syllabus + ", year = " + year + ", semester = " + semester;
	}
}
