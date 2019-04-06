
insert into "user" (id, login, email, language, password, role)
values (1, 'moderator', 'moderator1234@mailinator.com', 'PL', '$2a$10$s4DIGpWyJMBtx/ZAvgvdjOcAEe9XSAGc9XKbjo3pAixg5NEvAtZ0.', 'MODERATOR');

insert into position(id, name, description)
values (1, 'Kierownik działu IT', 'Bardzo ciekawy opis stanowiska');

insert into test(id, name, "group", language, position_id)
values (1, 'Test na stanowisko kierownika działu IT', 1, 'PL', 1);

insert into question (id, number, type, content, test_id)
values (1, 0, 'OPEN', 'treść pytania 1', 1);

insert into question (id, number, type, content, test_id)
values (2, 1, 'OPEN', 'treść pytania 2', 1);

insert into question (id, number, type, content, test_id)
values (3, 2, 'OPEN', 'treść pytania 3', 1);



SELECT setval('user_id_seq', (select max(id) from "user"), true);
SELECT setval('position_id_seq', (select max(id) from position), true);
SELECT setval('test_id_seq', (select max(id) from test), true);
SELECT setval('question_id_seq', (select max(id) from question), true);

