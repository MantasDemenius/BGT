create type user_role as enum ('BASIC', 'CREATOR', 'ADMINISTRATOR');

CREATE TABLE "public"."user" (
	id serial PRIMARY KEY,
	username text not null,
	email text not null,
	password text not null,
	"role" user_role,
	created_at TIMESTAMP NOT null default NOW()
);

CREATE TABLE "public".game (
	id serial PRIMARY KEY,
  language_id INT not null,
  user_id INT not null,
	author text,
	title text not null,
	description text,
	created_at TIMESTAMP NOT null default NOW()
);

create table "public".game_relationship (
	original_game_id INT not null,
	translated_game_id INT not null,
	constraint fk_original_game
	foreign key(original_game_id)
	references "public".game(id),
	constraint fk_translated_game
	foreign key(translated_game_id)
	references "public".game(id)
);


CREATE TABLE "public"."language" (
	id serial PRIMARY KEY,
	"name" text not null,
	code text not null,
	created_at TIMESTAMP NOT null default NOW()
);

alter table "public".game add constraint fk_game_language
foreign key(language_id)
references "public"."language" (id);

alter table "public".game add constraint fk_game_user
foreign key(user_id)
references "public"."user" (id);

create type component_category as enum ('RULES', 'CARD');

CREATE TABLE "public".component (
	id serial PRIMARY KEY,
  user_id INT not null,
	"category" component_category,
	title text,
	description text,
	"language_id" INT not null,
	created_at TIMESTAMP NOT null default NOW()
);

create table "public".component_relationship (
	original_component_id INT not null,
	translated_component_id INT not null,
	constraint fk_original_component
	foreign key(original_component_id)
	references "public".component(id),
	constraint fk_translated_component
	foreign key(translated_component_id)
	references "public".component(id)
);

alter table "public".component add constraint fk_component_language
foreign key(language_id)
references "public"."language" (id);

alter table "public".component add constraint fk_component_user
foreign key(user_id)
references "public"."user" (id);

create table "public".game_component (
	game_id INT not null,
	component_id INT not null,
	constraint fk_game_component_game
	foreign key(game_id)
	references "public".game(id),
	constraint fk_game_component_component
	foreign key (component_id)
	references "public".component(id)
);