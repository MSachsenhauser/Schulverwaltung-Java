###Erstellung der Datenbank Schulverwaltung

create database schulverwaltung;
use schulverwaltung;
alter database schulverwaltung charset=utf8;
 
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

create table absence
(
	id int primary key,
	studentId int,
	start	date,
	end		date,
	excusedByPhone int,
	excusedByEmail int,
	certificate	int
);
	
create table delay
(
	id int primary key,
	studentId int,
	start	date,
	end		date,
	description varchar(200),
	valid	int
);

create table subject
	(
		id int primary key,
		short varchar(20),
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

create table gradeGroup
	(
		id int primary key,
		gradeId int,
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
		group2subjectId int primary key,
		hour varchar(100),
		groupid int,
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
		description varchar(500),
		executionDate date,
		group2SubjectId int,
		announceDate date,
		maxPoints double,
		minPoints1 double,
		minPoints2 double,
		minPoints3 double,
		minPoints4 double,
		minPoints5 double,
		disableflag int default 0
	);

create table job 
	(
		id int primary key,
		description varchar (500),
		duration int,
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
		short varchar(10),
		phone varchar (100),
		email varchar (100),
		roomid int,
		birthday date,
		workhours dec(4,2),
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
		points double,
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

create table student2guardian
	(
		studentid int,
		guardianid int,
		primary key (studentid, guardianid)
	);


create table student2group
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
		weekday int,
		hour int,
		primary key (timetableid, weekday, hour)
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
		id int primary key auto_increment,
		groupid int,
		subjectid int,
		roomid int,
		teacherid int,
		disableflag int,
		description varchar(200)
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
	Job, Religion, Grade, disableflag
)
AS 
(
	SELECT student.Id, student.Name, student.Firstname,
	student.Street, student.City, student.plz, student.birthday, 
	entry, shortened, student.phone, student.email, 
	Concat(Concat(instructor.Name, ' '), instructor.Firstname), 
	company.Name, job.description, religion.description, grade.Description,
	student.disableflag 
	FROM Student 
	LEFT JOIN instructor ON instructorid = instructor.id 
	LEFT JOIN religion ON religionId = religion.Id 
	LEFT JOIN company ON instructor.companyId = company.id 
	LEFT JOIN job ON jobId = job.id
	LEFT JOIN student2group ON student.Id = student2group.studentId
	LEFT JOIN gradeGroup ON student2group.groupId = gradeGroup.id
	LEFT JOIN grade ON grade.id = gradeGroup.gradeId
	WHERE grade.disableflag = 0 or grade.Description is null
	Group By student.Id
);

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
	FROM religion LEFT JOIN subject on religion.subjectId = subject.id
);

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
	LEFT JOIN room on grade.roomId = room.id  
	LEFT JOIN teacher on grade.teacherId = teacher.id
);

CREATE OR REPLACE VIEW qryExam
(	
	id,
	type,
	description,
	executionDate,
	subject,
	teacher,
	room,
	gradeGroup,
	grade,
	announceDate,
	disableflag
)
AS
(
	SELECT exam.id, marktype.description, exam.description, exam.executionDate, 
		   subject.description, Concat(Concat(teacher.Name, ' '), 
		   teacher.Firstname), room.number, gradeGroup.description, grade.Description, exam.announceDate, exam.disableflag
	FROM exam
	INNER JOIN marktype ON exam.typeId = marktype.Id 
	INNER JOIN group2subject on exam.group2subjectId = group2subject.Id 
	INNER JOIN teacher on group2subject.teacherId = teacher.Id 
	INNER JOIN subject on group2subject.subjectId = subject.Id 
	INNER JOIN room on group2subject.roomid = teacher.roomid 
	INNER JOIN gradeGroup on group2subject.groupId = gradeGroup.id 
	INNER JOIN grade on gradeGroup.gradeId = grade.id 
	GROUP BY exam.id
);

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
	LEFT JOIN room on teacher.roomid = room.id
);

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
	LEFT JOIN company ON instructor.companyId = company.Id
);

CREATE OR REPLACE VIEW qrymark
(
	id,
	mark,
	student,
	trend,
	disableflag
)
AS
(
	SELECT mark.id, mark.mark, Concat(Concat(student.Name, ' '), 
		   student.Firstname), mark.trend, mark.disableflag
	FROM mark
	LEFT JOIN student on mark.studentId = student.id
);

CREATE OR REPLACE VIEW qrygrademaster
(
	student,
	grade
)
AS
(
	SELECT Concat(Concat(student.Name, ' '), 
		   student.Firstname), grade.description
	FROM grademaster
	LEFT JOIN grade on grademaster.gradeId = grade.id
	LEFT JOIN student on gradeMaster.studentId = student.id
);

insert into login
(login, password, email) values 
("Michael", "asdfg", "m.sachsenhauser@googlemail.com"),
("Nicole", "123", "nicole.uhb@googlemail.com"), 
("Administrator", "Administrator", "");

INSERT INTO MarkType
(id, description, weight, disableflag) VALUES 
(1, "Stegreifaufgabe", 1, 0),
(2, "Schulaufgabe", 2, 0),
(3, "Mündlich", 1, 0),
(4, "Kurzarbeit", 1, 0);

INSERT INTO typification
(id, description, disableflag) VALUES
(1, "Hauptschule", 0),
(2, "Realschule", 0),
(3, "Gymnasium", 0),
(4, "Universität", 0),
(5, "Keine", 0),
(6, "Fachoberschule", 0);


INSERT INTO room (id, number, description, disableflag) values (1, '1A1', 'Programmierraum', 0);
INSERT INTO teacher (id, name, firstname, short, email, roomid, workhours) VALUES (1, 'Mustermann', 'Max', 'MM', 'max@mustermann.de', 1, 40.0);
INSERT INTO grade (id, description, roomid, teacherId) VALUES (1, "IT12A", 1, 1);
INSERT INTO gradeGroup (id, gradeId, description) VALUES (1, 1, 'FIAE');
INSERT INTO gradeGroup (id, gradeId, description) VALUES (2, 1, 'FISI');
INSERT INTO subject (id, short, description) VALUES (1, 'AEP', 'Anwendungsentwicklung und -programmierung');
INSERT INTO group2subject (groupid, subjectid, roomid, teacherid, disableflag, description) values (1,1,1,1,0, '');

