drop table if exists evaluation cascade;
drop table if exists question cascade;
drop table if exists test_answer cascade;
drop table if exists "user" cascade;
drop table if exists position cascade;
drop table if exists test cascade;
drop table if exists question_answer cascade;
drop table if exists question_answer_evaluation cascade;

drop table if exists acl_entry cascade;
drop table if exists acl_object_identity cascade;
drop table if exists acl_class cascade;
drop table if exists acl_sid cascade;


create table "user"
(
    id                 bigserial primary key,
    login              varchar(100)       not null,
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
    language    varchar(30)                     not null,
    position_id bigint references position (id) not null
--     unique ("group", language)
);

-- FIXME ws https://stackoverflow.com/questions/12596023/import-postgresql-triggers-on-startup-hibernate
-- create sequence if not exists test_group_seq;
--
-- CREATE OR REPLACE FUNCTION fill_test_group_if_empty()
--     RETURNS trigger
--     LANGUAGE plpgsql VOLATILE
--     AS
-- $BODY$
-- BEGIN
--     IF NEW.group IS NULL THEN
--         NEW.group:= nextval('test_group_seq');
--     END IF;
--
--     RETURN NEW;
-- END
-- $BODY$;
--
-- CREATE TRIGGER fill_test_group
--     BEFORE INSERT
--     ON test
--     FOR EACH ROW
-- EXECUTE PROCEDURE fill_test_group_if_empty();

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
    test_id bigint references test (id),
    user_id bigint references "user" (id)
);

create table question_answer
(
    id          bigserial primary key,
    answer_id   bigint not null references test_answer (id),
    question_id bigint not null references question (id),
    content     text
);

create table evaluation
(
    id        bigserial primary key,
    content   text,
    answer_id bigint references test_answer (id)
);

create table question_answer_evaluation
(
    id                 bigserial primary key,
    evaluation_id      bigint not null references evaluation (id),
    question_answer_id bigint not null references question_answer (id),
    content            text
);

-- spring-acl tables

create table acl_sid
(
    id        bigserial    not null primary key,
    principal boolean      not null,
    sid       varchar(100) not null,
    constraint unique_uk_1 unique (sid, principal)
);

create table acl_class
(
    id            bigserial    not null primary key,
    class         varchar(100) not null,
    class_id_type varchar(100),
    constraint unique_uk_2 unique (class)
);

create table acl_object_identity
(
    id                 bigserial primary key,
    object_id_class    bigint      not null,
    object_id_identity varchar(36) not null,
    parent_object      bigint,
    owner_sid          bigint,
    entries_inheriting boolean     not null,
    constraint unique_uk_3 unique (object_id_class, object_id_identity),
    constraint foreign_fk_1 foreign key (parent_object) references acl_object_identity (id),
    constraint foreign_fk_2 foreign key (object_id_class) references acl_class (id),
    constraint foreign_fk_3 foreign key (owner_sid) references acl_sid (id)
);

create table acl_entry
(
    id                  bigserial primary key,
    acl_object_identity bigint  not null,
    ace_order           int     not null,
    sid                 bigint  not null,
    mask                integer not null,
    granting            boolean not null,
    audit_success       boolean not null,
    audit_failure       boolean not null,
    constraint unique_uk_4 unique (acl_object_identity, ace_order),
    constraint foreign_fk_4 foreign key (acl_object_identity) references acl_object_identity (id),
    constraint foreign_fk_5 foreign key (sid) references acl_sid (id)
);