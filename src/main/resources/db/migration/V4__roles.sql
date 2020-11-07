alter table "user"
drop column "role";

drop type if exists user_role;

create table "role" (
	id serial primary key,
	description varchar(255),
	"name" text);

create table user_role (
	user_id INT not null,
	role_id INT not null,
	constraint fk_user_role_user
	foreign key (user_id)
	references "user"(id),
	constraint fk_user_role_role
	foreign key (role_id)
	references "role"(id)
);

INSERT INTO role (id, description, name) VALUES (1, 'Basic role', 'BASIC');
INSERT INTO role (id, description, name) VALUES (2, 'Creator role', 'CREATOR');
INSERT INTO role (id, description, name) VALUES (3, 'ADMIN role', 'ADMIN');