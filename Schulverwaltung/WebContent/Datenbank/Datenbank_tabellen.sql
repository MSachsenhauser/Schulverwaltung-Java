###Erstellung der Datenbank Schulverwaltung

create database schulverwaltung;
use schulverwaltung

create table student 
	(
		id int primary key,
		name varchar(100),
		firstname varchar(100),
		street varchar (100),
		city varchar (100),
		plz varchar (100),
		birthday date,
		entry date,
		shortened boolean,
		phone varchar(100),
		email varchar (100),
		instructorid int,
		jobId int,
		religionId int,
		disableflag int default 0
	);

create table subject
	(
		id int primary key,
		description varchar(500),
		disableflag int default 0
	);

create table religion
	(
		id int primary key,
		description varchar(500),
		subjectid int,
		disableflag int default 0
	);

create table timetable
	(
		id int primary key,
		validTill date,
		disableflag int default 0
	);

create table group
	(
		id int primary key,
		description varchar(500),
		disableflag int default 0
	);

create table guardian
	(
		id int primary key,
		name varchar (100),
		firstname varchar(100),
		phone varchar (100),
		street varchar (100),
		city varchar (100),
		plz varchar (100),
		disableflag int default 0
	);

create table hour2subject
	(
		subjectid int primary key,
		hour varchar(100),
		groupid varchar(100),
		disableflag int default 0
	);

create table typification
	(
		id int primary key,
		description varchar (500),
		disableflag int default 0
	);

create table grade
	(
		id int primary key,
		description varchar (500),
		roomId int (100),
		teacherId int (100),
		disableflag int default 0
	);

create table exam
	(
		id int primary key,
		typeId int,
		executionDate date,
		subjectId int,
		teacherId int,
		announceDate date,
		disableflag int default 0
	);

create table job 
	(
		id int primary key,
		description varchar (500),
		duration int default 3,
		disableflag int default 0
	);

create table company
	(
		id int primary key,
		name varchar (100),
		street varchar (100),
		city varchar (100),
		plz varchar (100),
		phone varchar (100),
		disableflag int default 0
	);

create table teacher
	(
		id int primary key,
		name varchar (100),
		firstname varchar (100),
		phone varchar (100),
		email varchar (100),
		roomid int,
		birthday date,
		workhours dec(2,2),
		disableflag int default 0
	);

create table room
	(
		id int primary key,
		number varchar (20),
		description varchar (500),
		disableflag int default 0
	);

create table instructor
	(
		id int primary key,
		name varchar (100),
		firstname varchar (100),
		phone varchar (100),
		email varchar (100),
		companyid int,
		disableflag int default 0
	);

create table mark
	(
		id int primary key,
		mark int,
		studentid int,
		examid int,
		trend varchar (1),
		disableflag int default 0
	);

create table marktype
	(
		id int primary key,
		description varchar (500),
		weight decimal (4,2),
		disableflag int default 0
	);


create table group2grade
	(
		groupid int ,
		gradeid int,
		primary key (groupid, gradeid)
	);

create table student2guardian
	(
		studentid int,
		guardianid int,
		primary key (studentid, guardianid)
	);


create table student2groop
	(
		studentid int,
		groupid int,
		primary key(studentid, groupid)
	);


create table grademaster
	(
		studentid int,
		gradeid int,
		primary key (studentid, gradeid)
	);

create table plan2hour
	(
		timetableid int,
		subjectid int,
		weekday varchar (100),
		groupid int,
		hour int,
		primary key (timetableid, weekday, hour, groupid)
	);

create table student2company
	(
		studentid int,
		companyid int,
		primary key (studentid, companyid)
	);

create table student2typification
	(
		studentid int,
		typificationid int,
		primary key (studentid, typificationid)
	);

create table group2subject
	(
		groupid int,
		subjectid int,
		roomid int,
		teacherid int,
		primary key (groupid, subjectid)
	);

create table free2subjekt
	(
		studentid int,
		subjectid int,
		freedate date,
		primary key (studentid, subjectid)
	);

create table login
	(
		login varchar(60) primary key,
		password varchar(20),
		email varchar (100)
	);

CREATE OR REPLACE VIEW qryStudent 
(
	Id, Name, Firstname, Street, City, plz, birthday, 
	entry, shortened, phone, email, Instructor, Company, 
	Job, Religion, disableflag
)
AS 
(
	SELECT student.Id, student.Name, student.Firstname,
	student.Street, student.City, student.plz, student.birthday, 
	entry, shortened, student.phone, student.email, 
	Concat(Concat(instructor.Name, ' '), instructor.Firstname), 
	company.Name, job.description, religion.description, 
	student.disableflag 
	FROM Student 
	INNER JOIN instructor ON instructorid = instructor.id 
	INNER JOIN religion ON religionId = religion.Id 
	INNER JOIN company ON instructor.companyId = company.id 
	INNER JOIN job ON jobId = job.id
) 
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qryReligion
(
		id,
		description,
		subject,
		disableflag
)
AS
(
	SELECT religion.id, religion.description, subject.description, religion.disableflag 
	FROM religion INNER JOIN subject on religion.subjectId = subject.id
)
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qryGrade
(
	id,
	description,
	room,
	teacher,
	disableflag
)
AS
(
	SELECT grade.id, grade.description, room.number, 
	       Concat(Concat(teacher.Name, ' '), teacher.Firstname), grade.disableflag
	FROM grade
	INNER JOIN room on grade.roomId = room.id  
	INNER JOIN teacher on grade.teacherId = teacher.id
)
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qryExam
(	
	id,
	type,
	executionDate,
	subject,
	teacher,
	announceDate,
	disableflag
)
AS
(
	SELECT exam.id, typification.description, exam.executionDate, 
		   subject.description, Concat(Concat(teacher.Name, ' '), 
		   teacher.Firstname), exam.announceDate, exam.disableflag
	FROM exam
	INNER JOIN marktype ON exam.typeId = marktype.Id 
	INNER JOIN subject on exam.subjectId = subject.Id 
	INNER JOIN teacher on exam.teacherId = teacher.Id 
)
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qryTeacher
(
	id,
	name,
	firstname,
	phone,
	email,
	room,
	birthday,
	workhours,
	disableflag
)
AS
(
	SELECT teacher.id, teacher.name, teacher.firstname, teacher.phone, teacher.email, room.number,
		   teacher.birthday, teacher.workhours, teacher.disableflag
	FROM teacher
	INNER JOIN room on teacher.roomid = room.id
)
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qryInstructor
(
	id,
	name,
	firstname,
	phone,
	email,
	company,
	disableflag
)
AS
(
	SELECT instructor.id, instructor.name, instructor.firstname, instructor.phone, 
		   instructor.email, company.name, instructor.disableflag
	FROM instructor
	INNER JOIN company ON instructor.companyId = company.Id
)
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qry_mark
(
	id,
	mark,
	student,
	exam,
	trend,
	disableflag
)
AS
(
	SELECT mark.id, mark.mark, Concat(Concat(student.Name, ' '), 
		   student.Firstname), mark.trend, mark.disableflag
	FROM mark
	INNER JOIN student on mark.studentId = student.id
)
WITH CHECK OPTION;

CREATE OR REPLACE VIEW qry_grademaster
(
	student,
	grade
)
AS
(
	SELECT Concat(Concat(student.Name, ' '), 
		   student.Firstname), grade.description
	FROM grademaster
	INNER JOIN grade on grademaster.gradeId = grade.id
	INNER JOIN student on gradeMaster.studentId = student.id
)
WITH CHECK OPTION;

insert into login
(login, password, email) values 
("Michael", "asdfg", "m.sachsenhauser@googlemail.com"),
("Nicole", "123", "nicole.uhb@googlemail.com"), 
("Administrator", "Administrator", "");