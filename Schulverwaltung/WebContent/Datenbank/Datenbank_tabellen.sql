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
		intructorid int,
		jobId int,
		religionId int,
		disableflag int
	);

create table subject
	(
		id int primary key,
		description varchar(500),
		disableflag int
	);

create table religion
	(
		id int primary key,
		description varchar(500),
		subjectid int,
		disableflag int
	);

create table timetable
	(
		id int primary key,
		validTill date,
		disableflag int
	);

create table group
	(
		id int primary key,
		description varchar(500),
		disableflag int
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
		disableflag int
	);

create table hour2subject
	(
		subjectid int primary key,
		hour varchar(100),
		groupid varchar(100),
		disableflag int
	);

create table typification
	(
		id int primary key,
		description varchar (500),
		disableflag int
	);

create table grade
	(
		id int primary key,
		description varchar (500),
		roomId int (100),
		teacherId int (100),
		disableflag int
	);

create table exam
	(
		id int primary key,
		typeId int,
		examdate date,
		subjectId int,
		teacherId int,
		disableflag int
	);

create table job 
	(
		id int primary key,
		description varchar (500),
		duration int,
		disableflag int
	);

create table company
	(
		id int primary key,
		name varchar (100),
		street varchar (100),
		city varchar (100),
		plz varchar (100),
		phone varchar (100),
		disableflag int
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
		disableflag int
	);

create table room
	(
		id int primary key,
		number varchar (20),
		description varchar (500),
		disableflag int
	);

create table instructor
	(
		id int primary key,
		name varchar (100),
		firstname varchar (100),
		phone varchar (100),
		email varchar (100),
		companyid int,
		disableflag int
	);

create table mark
	(
		id int primary key,
		mark int,
		studentid int,
		examid int,
		trend varchar (1),
		disableflag int
	);

create table marktype
	(
		id int primary key,
		description varchar (500),
		weight decimal (4,2),
		disableflag int
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


insert into login
(login, password, email) values 
("Michael", "asdfg", "m.sachsenhauser@googlemail.com"),
("Nicole", "123", "nicole.uhb@googlemail.com"), 
("Administrator", "Administrator", "");