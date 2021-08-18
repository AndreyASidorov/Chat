CREATE SCHEMA "chat";
CREATE TABLE "chat".user_role
(
    id SERIAL PRIMARY KEY,
    user_role CHARACTER VARYING(20) not null

);
INSERT INTO "chat".user_role (user_role) values ('admin'),
                                                ('moderator'),
                                                ('user');
