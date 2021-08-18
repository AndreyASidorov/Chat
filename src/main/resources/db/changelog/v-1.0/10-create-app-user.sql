CREATE TABLE "chat".app_user
(
    id SERIAL PRIMARY KEY,
    login CHARACTER VARYING(20) not null,
    user_name CHARACTER VARYING(20) not null,
    password text not null,
    is_active boolean DEFAULT true,
    user_role_id INTEGER REFERENCES "chat".user_role(id)

);
INSERT INTO "chat".app_user (login, user_name, password, user_role_id) values ('andrey','Andrey','1111',1),
                                                                              ('YoutubeBot','yBot','1111',3),
                                                                              ('moderator','Moderator','1111',2),
                                                                              ('user','User','1111',3);