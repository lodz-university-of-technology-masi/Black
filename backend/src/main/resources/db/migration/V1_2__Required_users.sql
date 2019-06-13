-- Each password is: pass

CREATE OR REPLACE FUNCTION create_user(user_login varchar(100), user_role varchar(30))
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN

    -- create user
    INSERT INTO public."user" (login, email, language, password, role)
    VALUES (user_login, CONCAT(user_login,'@mailinator.com'), 'PL', '$2a$11$FJpv8AHq/CcPEURzPiVWaeVmLObsOSgZwWiXnAadwHpbidemol8ES', user_role);

    -- create acl
    INSERT INTO public.acl_sid (principal, sid) VALUES (true, user_login);
    INSERT INTO public.acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
        VALUES (5, currval('user_id_seq'), null, currval('acl_sid_id_seq'), false);
END
$$;
DO $$ BEGIN

    -- tester accounts
    PERFORM create_user('atester1', 'MODERATOR');
    PERFORM create_user('rtester1', 'REDACTOR');

    PERFORM create_user('atester2', 'MODERATOR');
    PERFORM create_user('rtester2', 'REDACTOR');

    PERFORM create_user('atester3', 'MODERATOR');
    PERFORM create_user('rtester3', 'REDACTOR');

    -- Blue
    PERFORM create_user('ablue1', 'MODERATOR');
    PERFORM create_user('rblue1', 'REDACTOR');

    PERFORM create_user('ablue2', 'MODERATOR');
    PERFORM create_user('rblue2', 'REDACTOR');

    PERFORM create_user('ablue3', 'MODERATOR');
    PERFORM create_user('rblue3', 'REDACTOR');

    -- Yellow
    PERFORM create_user('ayellow1', 'MODERATOR');
    PERFORM create_user('ryellow1', 'REDACTOR');

    PERFORM create_user('ayellow2', 'MODERATOR');
    PERFORM create_user('ryellow2', 'REDACTOR');

    PERFORM create_user('ayellow3', 'MODERATOR');
    PERFORM create_user('ryellow3', 'REDACTOR');

    PERFORM create_user('ayellow4', 'MODERATOR');
    PERFORM create_user('ryellow4', 'REDACTOR');

    --Green
    PERFORM create_user('agreen1', 'MODERATOR');
    PERFORM create_user('rgreen1', 'REDACTOR');

    PERFORM create_user('agreen2', 'MODERATOR');
    PERFORM create_user('rgreen2', 'REDACTOR');

    PERFORM create_user('agreen3', 'MODERATOR');
    PERFORM create_user('rgreen3', 'REDACTOR');

    -- Red
    PERFORM create_user('ared1', 'MODERATOR');
    PERFORM create_user('rred1', 'REDACTOR');

    PERFORM create_user('ared2', 'MODERATOR');
    PERFORM create_user('rred2', 'REDACTOR');

    PERFORM create_user('ared3', 'MODERATOR');
    PERFORM create_user('rred3', 'REDACTOR');

    PERFORM create_user('ared4', 'MODERATOR');
    PERFORM create_user('rred4', 'REDACTOR');

    --Orange
    PERFORM create_user('aorange1', 'MODERATOR');
    PERFORM create_user('rorange1', 'REDACTOR');

    PERFORM create_user('aorange2', 'MODERATOR');
    PERFORM create_user('rorange2', 'REDACTOR');

    PERFORM create_user('aorange3', 'MODERATOR');
    PERFORM create_user('rorange3', 'REDACTOR');

    PERFORM create_user('aorange4', 'MODERATOR');
    PERFORM create_user('rorange4', 'REDACTOR');

    --Black
    PERFORM create_user('ablack1', 'MODERATOR');
    PERFORM create_user('rblack1', 'REDACTOR');

    PERFORM create_user('ablack2', 'MODERATOR');
    PERFORM create_user('rblack2', 'REDACTOR');

    PERFORM create_user('ablack3', 'MODERATOR');
    PERFORM create_user('rblack3', 'REDACTOR');

    PERFORM create_user('ablack4', 'MODERATOR');
    PERFORM create_user('rblack4', 'REDACTOR');

    PERFORM create_user('ablack5', 'MODERATOR');
    PERFORM create_user('rblack5', 'REDACTOR');

    --White
    PERFORM create_user('awhite1', 'MODERATOR');
    PERFORM create_user('rwhite1', 'REDACTOR');

    PERFORM create_user('awhite2', 'MODERATOR');
    PERFORM create_user('rwhite2', 'REDACTOR');

    PERFORM create_user('awhite3', 'MODERATOR');
    PERFORM create_user('rwhite3', 'REDACTOR');

    --Magenta
    PERFORM create_user('amagenta1', 'MODERATOR');
    PERFORM create_user('rmagenta1', 'REDACTOR');

    PERFORM create_user('amagenta2', 'MODERATOR');
    PERFORM create_user('rmagenta2', 'REDACTOR');

    PERFORM create_user('amagenta3', 'MODERATOR');
    PERFORM create_user('rmagenta3', 'REDACTOR');

    PERFORM create_user('amagenta4', 'MODERATOR');
    PERFORM create_user('rmagenta4', 'REDACTOR');

    PERFORM create_user('amagenta5', 'MODERATOR');
    PERFORM create_user('rmagenta5', 'REDACTOR');

    --Violet
    PERFORM create_user('aviolet1', 'MODERATOR');
    PERFORM create_user('rviolet1', 'REDACTOR');

    PERFORM create_user('aviolet2', 'MODERATOR');
    PERFORM create_user('rviolet2', 'REDACTOR');

    PERFORM create_user('aviolet3', 'MODERATOR');
    PERFORM create_user('rviolet3', 'REDACTOR');

    PERFORM create_user('aviolet4', 'MODERATOR');
    PERFORM create_user('rviolet4', 'REDACTOR');

END $$;

DROP FUNCTION create_user;





