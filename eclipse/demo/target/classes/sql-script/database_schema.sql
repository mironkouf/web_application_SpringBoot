select * from course;
select * from student_registration;
show tables;
show databases;

drop table student_registration;
drop table course;
drop database texnologia1;

create database texnologia1;
use texnologia1;

CREATE TABLE course (
	course_id int NOT NULL AUTO_INCREMENT,
    name varchar(20),
    login varchar(20),
    syllabus varchar(20),
    year int,
    semester int,
    projectper double,
    examsper double,
    projectav double,
    examsav double,
    finalav double,
    min double,
    max double,
    mean double,
    median double,
    PRIMARY KEY(course_id)
);
CREATE TABLE student_registration (
	student_id int NOT NULL AUTO_INCREMENT,
    course_id1 int,
    student_name varchar(20),
    year_of_studies int,
    student_semester int,
    project_grade double,
    exams_grade double,
    final_grade double default NULL,
	PRIMARY KEY(student_id),
    FOREIGN KEY (course_id1) REFERENCES course(course_id)
);

INSERT INTO `course` VALUES 
	(1,'Mathimatika','kathigitis 1','pithanotites',2,3,0.5,0.5,0,0,0,0,0,0,0),
	(2,'Texnologia','kathigitis 2','programmatismos',4,8,0.5,0.5,0,0,0,0,0,0,0);
INSERT INTO `student_registration` VALUES 
	(1,1,'Apostolos Vasiliadis',2,3,10,9,0),
	(2,2,'Panos Zarras',4,8,9,10,0);




