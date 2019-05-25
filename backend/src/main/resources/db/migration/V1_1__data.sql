INSERT INTO public.acl_class (id, class, class_id_type)
VALUES (1, 'pl.masi.entity.Position', null);
INSERT INTO public.acl_class (id, class, class_id_type)
VALUES (2, 'pl.masi.entity.Test', null);
INSERT INTO public.acl_class (id, class, class_id_type)
VALUES (3, 'pl.masi.entity.TestAnswer', null);
INSERT INTO public.acl_class (id, class, class_id_type)
VALUES (4, 'pl.masi.entity.Evaluation', null);
INSERT INTO public.acl_class (id, class, class_id_type)
VALUES (5, 'pl.masi.entity.User', null);

INSERT INTO public.acl_sid (id, principal, sid)
VALUES (1, true, 'moderator');
INSERT INTO public.acl_sid (id, principal, sid)
VALUES (2, true, 'redaktor');
INSERT INTO public.acl_sid (id, principal, sid)
VALUES (3, true, 'kandydat');
INSERT INTO public.acl_sid (id, principal, sid)
VALUES (4, true, 'redaktor2');

INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (1, 1, '1', null, 1, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (2, 1, '2', null, 1, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (3, 2, '1', null, 2, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (4, 2, '3', null, 2, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (5, 3, '1', null, 3, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (6, 4, '1', null, 2, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (7, 5, '1', null, 1, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (8, 5, '2', null, 2, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (9, 5, '3', null, 3, false);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid,
                                        entries_inheriting)
VALUES (10, 5, '4', null, 4, false);

INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (1, 1, 0, 1, 27, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (2, 2, 0, 1, 27, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (3, 3, 0, 2, 27, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (4, 4, 0, 2, 27, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (5, 5, 0, 3, 27, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (6, 6, 0, 2, 27, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (7, 3, 1, 3, 1, true, false, true);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (8, 5, 1, 2, 1, true, false, true);

INSERT INTO public."user" (id, login, email, language, password, role, registration_token)
VALUES (1, 'moderator', 'moderator1234@mailinator.com', 'PL',
        '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'MODERATOR', null);
INSERT INTO public."user" (id, login, email, language, password, role, registration_token)
VALUES (2, 'kandydat', 'kandydat1234@mailinator.com', 'PL',
        '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'CANDIDATE', null);
INSERT INTO public."user" (id, login, email, language, password, role, registration_token)
VALUES (3, 'redaktor', 'redaktor1234@mailinator.com', 'PL',
        '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'REDACTOR', null);
INSERT INTO public."user" (id, login, email, language, password, role, registration_token)
VALUES (4, 'redaktor2', 'redaktor21234@mailinator.com', 'PL',
        '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'REDACTOR', null);

INSERT INTO public.position (id, name, description, active)
VALUES (1, 'Kierownik działu IT', 'Bardzo ciekawy opis stanowiska', true);
INSERT INTO public.position (id, name, description, active)
VALUES (2, 'Kierownik działu HR', 'Bardzo ciekawy opis stanowiska', true);

INSERT INTO public.test (id, name, "group", language, position_id)
VALUES (1, 'Test na stanowisko kierownika działu IT', 1, 'PL', 1);
INSERT INTO public.test (id, name, "group", language, position_id)
VALUES (3, 'Test na stanowisko kierownika działu HR', 2, 'PL', 2);

INSERT INTO public.question (id, number, type, content, body, test_id)
VALUES (1, 0, 'CHOICE', 'treść pytania 1 - pytanie wyboru', 'odpowiedź 1;odpowiedź 2;odpowiedź 3', 1);
INSERT INTO public.question (id, number, type, content, body, test_id)
VALUES (2, 1, 'SCALE', 'treść pytania 2 - pytanie skali', '1;15.5;0.5', 1);
INSERT INTO public.question (id, number, type, content, body, test_id)
VALUES (3, 2, 'NUMBER', 'treść pytania 3 - pytanie liczbowe', NULL, 1);
INSERT INTO public.question (id, number, type, content, body, test_id)
VALUES (4, 0, 'OPEN', 'treść pytania 1', NULL, 3);
INSERT INTO public.question (id, number, type, content, body, test_id)
VALUES (5, 1, 'OPEN', 'treść pytania 2', NULL, 3);
INSERT INTO public.question (id, number, type, content, body, test_id)
VALUES (6, 2, 'OPEN', 'treść pytania 3', NULL, 3);

INSERT INTO public.test_answer (id, content, test_id, user_id, evaluated)
VALUES (1, 'bardzo istotne pola', 1, 2, true);

INSERT INTO public.question_answer (id, answer_id, type, body, number)
VALUES (1, 1, 'CHOICE', '1;2', 0);
INSERT INTO public.question_answer (id, answer_id, type, body, number)
VALUES (2, 1, 'SCALE', '9.5', 1);
INSERT INTO public.question_answer (id, answer_id, type , body, number)
VALUES (3, 1, 'NUMBER', '1234.334', 2);

INSERT INTO public.evaluation (id, content, answer_id)
VALUES (1, 'komentarz do test', 1);

INSERT INTO public.question_answer_evaluation (id, evaluation_id, number, content)
VALUES (1, 1, 0, 'Ocena odpowiedzi 1');
INSERT INTO public.question_answer_evaluation (id, evaluation_id, number, content)
VALUES (2, 1, 1, 'Ocena odpowiedzi 2');
INSERT INTO public.question_answer_evaluation (id, evaluation_id, number, content)
VALUES (3, 1, 2, 'Ocena odpowiedzi 3');


SELECT setval('user_id_seq', (select max(id) from "user"), true);
SELECT setval('position_id_seq', (select max(id) from position), true);
SELECT setval('test_id_seq', (select max(id) from test), true);
SELECT setval('question_id_seq', (select max(id) from question), true);
SELECT setval('test_answer_id_seq', (select max(id) from test_answer), true);
SELECT setval('question_answer_id_seq', (select max(id) from question_answer), true);
SELECT setval('evaluation_id_seq', (select max(id) from evaluation), true);
SELECT setval('question_answer_evaluation_id_seq', (select max(id) from question_answer_evaluation), true);


SELECT setval('acl_class_id_seq', (select max(id) from acl_class), true);
SELECT setval('acl_sid_id_seq', (select max(id) from acl_sid), true);
SELECT setval('acl_object_identity_id_seq', (select max(id) from acl_object_identity), true);
SELECT setval('acl_entry_id_seq', (select max(id) from acl_entry), true);

SELECT setval('test_group_seq', (select max("group") from test), true);

