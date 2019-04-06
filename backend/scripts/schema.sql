drop table if exists position;
drop table if exists  evalutation;
drop table if exists answer;
drop table if exists "user";
drop table if exists test;

create table test (
	id serial primary key,
	polish_test text,
	english_test text
);

create table "user" (
	id serial primary key,
	login varchar (100),
	email varchar (50),
	language varchar (30),
	password varchar (100),
	role varchar (30)
);

create table answer (
	id serial primary key,
	polish_answer text,
	english_answer text,
	test_id integer references test(id),
	user_id integer references "user"(id)
);

create table evalutation (
	id serial primary key,
	polish_evaluation text,
	english_evaluation text,
	answer_id integer references answer(id)
);

create table position (
	id serial primary key,
	name varchar (100),
	active boolean,
	test_id integer references test(id)
);