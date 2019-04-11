-- users
insert into "user" (id, login, email, language, password, role)
values (1, 'moderator', 'moderator1234@mailinator.com', 'PL', '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'MODERATOR');

insert into "user" (id, login, email, language, password, role)
values (2, 'kandydat', 'kandydat1234@mailinator.com', 'PL', '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'CANDIDATE');

-- positions
insert into position(id, name, description)
values (1, 'Kierownik działu IT', 'Bardzo ciekawy opis stanowiska');

insert into position(id, name, description)
values (2, 'Kierownik działu HR', 'Bardzo ciekawy opis stanowiska');


-- tests
insert into test(id, name, "group", language, position_id)
values (1, 'Test na stanowisko kierownika działu IT', 1, 'PL', 1);

-- questions
insert into question (id, number, type, content, test_id)
values (1, 0, 'OPEN', 'treść pytania 1', 1);

insert into question (id, number, type, content, test_id)
values (2, 1, 'OPEN', 'treść pytania 2', 1);

insert into question (id, number, type, content, test_id)
values (3, 2, 'OPEN', 'treść pytania 3', 1);

--test answers
insert into test_answer(id, content, test_id, user_id)
values (1, 'bardzo istotne pola', 1, 2);

-- question answers
insert into question_answer(id, answer_id, question_id, content)
values (1, 1, 1, 'odpowiedz na pyt 1');

insert into question_answer(id, answer_id, question_id, content)
values (2, 1, 2, 'odpowiedz na pyt 2');

insert into question_answer(id, answer_id, question_id, content)
values (3, 1, 3, 'odpowiedz na pyt 3');

-- evaluations
insert into evaluation(id, content, answer_id)
values (1, 'komentarz do test', 1);

-- question answers evaluations
insert into question_answer_evaluation(id, evaluation_id, question_answer_id, content)
values (1, 1, 1, 'Ocena odpowiedzi 1');

insert into question_answer_evaluation(id, evaluation_id, question_answer_id, content)
values (2, 1, 2, 'Ocena odpowiedzi 2');

insert into question_answer_evaluation(id, evaluation_id, question_answer_id, content)
values (3, 1, 3, 'Ocena odpowiedzi 3');

SELECT setval('user_id_seq', (select max(id) from "user"), true);
SELECT setval('position_id_seq', (select max(id) from position), true);
SELECT setval('test_id_seq', (select max(id) from test), true);
SELECT setval('question_id_seq', (select max(id) from question), true);
SELECT setval('test_answer_id_seq', (select max(id) from test_answer), true);
SELECT setval('question_answer_id_seq', (select max(id) from question_answer), true);
SELECT setval('evaluation_id_seq', (select max(id) from evaluation), true);
SELECT setval('question_answer_evaluation_id_seq', (select max(id) from question_answer_evaluation), true);

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, true, 'kandydat'),
(2, true, 'redaktor'),
(3, false, 'ROLE_EDITOR');

INSERT INTO acl_class (id, class) VALUES
(1, 'pl.masi.entity.Evaluation'),
(2, 'pl.masi.entity.Position');


INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 1, false),
(2, 2, 1, NULL, 1, false),
(3, 2, 2, NULL, 1, false);



INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, true, true, false),
-- (2, 2, 1, 1, 0, true, true, false),
(3, 3, 1, 1, 1, true, true, false);
