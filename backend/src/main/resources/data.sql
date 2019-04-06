
insert into "user" (id, login, email, language, password, role)
values (1, 'moderator', 'moderator1234@mailinator.com', 'PL', 'md5password', 'MODERATOR');

insert into position(id, name, description)
values (1, 'Kierownik działu IT', 'Bardzo ciekawy opis stanowiska');

insert into test(id, name, "group", language, position_id)
values (1, 'Test na stanowisko kierownika działu IT', 1, 'PL', 1);


SELECT setval('user_id_seq', (select max(id) from "user"), true);
SELECT setval('position_id_seq', (select max(id) from position), true);
SELECT setval('test_id_seq', (select max(id) from test), true);

