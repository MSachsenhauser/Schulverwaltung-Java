###Erstellung der Datenbank Schulverwaltung

create database schulverwaltung;
use schulverwaltung

create table schueler 
	(
		schuelerid int primary key,
		nachname varchar(100),
		vorname varchar(100),
		strasse varchar (100),
		ort varchar (100),
		plz varchar (100),
		geburtsdatum date,
		eintrittsdatum date,
		verkuerzt boolean,
		telefon varchar(100),
		email varchar (100),
		ausbilderid int,
		berufid int,
		religionid int,
		disableflag int
	);

create table fach
	(
		facid int primary key,
		bezeichnung varchar(500),
		disableflag int
	);

create table religion
	(
		religionid int primary key,
		bezeichnung varchar(500),
		fachid int,
		disableflag int
	);

create table stundenplan
	(
		stundenplanid int primary key,
		gueltigbis date,
		disableflag int
	);

create table gruppe
	(
		gruppenid int primary key,
		bezeichnung varchar(500),
		disableflag int
	);

create table erziehungsberechtigte
	(
		erzbid int primary key,
		nachname varchar (100),
		vorname varchar(100),
		telefon varchar (100),
		strasse varchar (100),
		ort varchar (100),
		plz varchar (100),
		disableflag int
	);

create table stunden2fach
	(
		fachid int primary key,
		stunden varchar(100),
		gruppeid varchar(100),
		disableflag int
	);

create table vorbildung
	(
		vorbildungid int primary key,
		bezeichnung varchar (500),
		disableflag int
	);

create table klasse
	(
		klasseid int primary key,
		bezeichnung varchar (500),
		klassenzimmerid int (100),
		klassenleiterid int (100),
		disableflag int
	);

create table pruefung
	(
		pruefungid int primary key,
		typid int,
		datum date,
		fachid int,
		lehrerid int,
		disableflag int
	);

create table beruf 
	(
		berufid int primary key,
		bezeichnung varchar (500),
		dauer int,
		disableflag int
	);

create table betrieb
	(
		betriebid int primary key,
		name varchar (100),
		strasse varchar (100),
		ort varchar (100),
		plz varchar (100),
		telefon varchar (100),
		disableflag int
	);

create table lehrer
	(
		lehrerid int primary key,
		nachname varchar (100),
		vorname varchar (100),
		telefon varchar (100),
		email varchar (100),
		raumid int,
		geburtstag date,
		arbeitsstunden dec(2,2),
		disableflag int
	);

create table raum
	(
		raumid int primary key,
		raumnummer varchar (20),
		bezeichnung varchar (500),
		disableflag int
	);

create table ausbilder
	(
		ausbilderid int primary key,
		nachname varchar (100),
		vorname varchar (100),
		telefon varchar (100),
		email varchar (100),
		betriebid int,
		disableflag int
	);

create table note
	(
		noteid int primary key,
		note int,
		schuelerid int,
		pruefungid int,
		tendenz varchar (1),
		disableflag int
	);

create table note2typ
	(
		typid int primary key,
		bezeichnung varchar (500),
		gewichtung decimal (4,2),
		disableflag int
	);


create table gruppe2klasse
	(
		gruppeid int ,
		klasseid int,
		primary key (gruppeid, klasseid)
	);

create table schueler2erzb
	(
		schuelerid int,
		erzbid int,
		primary key (schuelerid, erzbid)
	);


create table schueler2gruppe
	(
		schuelerid int,
		gruppeid int,
		primary key(schuelerid, gruppeid)
	);


create table klassensprecher
	(
		schuelerid int,
		klasseid int,
		primary key (schuelerid, klasseid)
	);

create table plan2stunden
	(
		stundenplanid int,
		fachid int,
		wochentag varchar (100),
		gruppeid int,
		stunde int,
		primary key (stundenplanid, wochentag, stunde, gruppeid)
	);

create table schueler2betrieb
	(
		schuelerid int,
		betriebid int,
		primary key (schuelerid, betriebid)
	);

create table schueler2votbildung
	(
		schuelerid int,
		votbildungid int,
		primary key (schuelerid, votbildungid)
	);

create table gruppe2fach
	(
		gruppeid int,
		fachid int,
		raumid int,
		lehrerid int,
		primary key (gruppeid, fachid)
	);

create table befreite2fach
	(
		schuelerid int,
		fachid int,
		antragsdatum date,
		primary key (schuelerid, fachid)
	);

create table login
	(
		login varchar(60) primary key,
		passwort varchar(20),
		email varchar (100)
	);


insert into login
(login, passwort, email) values 
("Michael", "asdfg", "m.sachsenhauser@googlemail.com"),
("Nicole", "123", "nicole.uhb@googlemail.com"), 
("Administrator", "Administrator", "");
