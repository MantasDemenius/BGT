CREATE TABLE "public".game (
	id serial PRIMARY KEY,
	title text not null,
	description text,
	language_id INT not null,
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

CREATE TABLE "public".card (
	id serial PRIMARY KEY,
	title text not null,
	description text,
	"language_id" INT not null,
	created_at TIMESTAMP NOT null default NOW()
);

create table "public".card_relationship (
	original_card_id INT not null,
	translated_card_id INT not null,
	constraint fk_original_card
	foreign key(original_card_id)
	references "public".card(id),
	constraint fk_translated_card
	foreign key(translated_card_id)
	references "public".card(id)
);

alter table "public".card add constraint fk_card_language
foreign key(language_id)
references "public"."language" (id);

create table "public".game_card (
	id serial primary key,
	game_id INT not null,
	card_id INT not null,
	constraint fk_game_card_game
	foreign key(game_id)
	references "public".game(id),
	constraint fk_game_card_card
	foreign key (card_id)
	references "public".card(id)
);