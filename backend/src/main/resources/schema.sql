drop table if exists evaluation cascade;
drop table if exists question cascade;
drop table if exists test_answer cascade;
drop table if exists "user" cascade;
drop table if exists position cascade;
drop table if exists test cascade;
drop table if exists question_answer cascade;
drop table if exists question_answer_evaluation cascade;


create table "user"
(
  id                 bigserial primary key,
  login              varchar(100) not null ,
  email              varchar(50) unique not null,
  language           varchar(30),
  password           varchar(100),
  role               varchar(30),
  registration_token varchar(255)
);


create table position
(
  id          bigserial primary key,
  name        varchar(100) not null,
  description varchar(300),
  active      boolean      not null default true
);

create table test
(
  id          bigserial primary key,
  name        varchar(100),
  "group"     bigint,
  language    varchar(30) not null ,
  position_id bigint references position (id) not null,
  unique ("group", language)
);

create table question
(
  id      bigserial primary key,
  number  integer,
  type    varchar(10),
  content text,
  test_id bigint references test (id) not null
);

create table test_answer
(
  id      bigserial primary key,
  content text,
  test_id integer references test (id),
  user_id integer references "user" (id)
);

create table question_answer
(
  id          bigserial primary key,
  answer_id   integer not null references test_answer (id),
  question_id integer not null references question (id),
  content     text
);

create table evaluation
(
  id        bigserial primary key,
  content   text,
  answer_id integer references test_answer (id)
);

create table question_answer_evaluation
(
  id                 bigserial primary key,
  evaluation_id      integer not null references evaluation (id),
  question_answer_id integer not null references question_answer (id),
  content            text
);
