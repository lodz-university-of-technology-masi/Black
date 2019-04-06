drop table if exists evalutation cascade;
drop table if exists question cascade;
drop table if exists test_answer cascade;
drop table if exists "user" cascade;
drop table if exists position cascade;
drop table if exists test cascade;
drop table if exists question_answer cascade;
drop table if exists question_answer_evaluation cascade;

create table position (
	id serial primary key,
	name varchar (100),
	active boolean
);

create table test (
	id serial primary key,
	"group" integer,
	language varchar (10),
	name varchar (100),
	position_id integer references position(id),
	unique ("group", language)
);

create table question (
	id serial primary key,
	type varchar(10),
	content text,
	test_id integer references test(id)
);

create table "user" (
	id serial primary key,
	login varchar (100),
	email varchar (50),
	language varchar (30),
	password varchar (100),
	role varchar (30)
);

create table test_answer (
	id serial primary key,
	content text,
	test_id integer references test(id),
	user_id integer references "user"(id)
);

create table question_answer (
	id serial primary key,
	answer_id integer not null references test_answer(id),
	question_id integer not null references question(id),
	content text
);

create table evalutation (
	id serial primary key,
	content text,
	answer_id integer references test_answer(id)
);

create table question_answer_evaluation 
(
	id serial primary key,
	evalutation_id integer not null references evalutation(id),
	question_answer_id integer not null references question_answer(id),
	content text
);
