CREATE TABLE "BGTMIN".game (
	id serial PRIMARY KEY,
	title text not null,
	description text,
	language_id INT not null,
	created_at TIMESTAMP NOT null default NOW()
);

create table "BGTMIN".game_relationship (
	original_game_id INT not null,
	translated_game_id INT not null,
	constraint fk_original_game
	foreign key(original_game_id)
	references "BGTMIN".game(id),
	constraint fk_translated_game
	foreign key(translated_game_id)
	references "BGTMIN".game(id)
);


CREATE TABLE "BGTMIN"."language" (
	id serial PRIMARY KEY,
	"name" text not null,
	code text not null,
	created_at TIMESTAMP NOT null default NOW()
);

alter table "BGTMIN".game add constraint fk_game_language
foreign key(language_id)
references "BGTMIN"."language" (id);

CREATE TABLE "BGTMIN".card (
	id serial PRIMARY KEY,
	title text not null,
	description text,
	"language_id" INT not null,
	created_at TIMESTAMP NOT null default NOW()
);

create table "BGTMIN".card_relationship (
	original_card_id INT not null,
	translated_card_id INT not null,
	constraint fk_original_card
	foreign key(original_card_id)
	references "BGTMIN".card(id),
	constraint fk_translated_card
	foreign key(translated_card_id)
	references "BGTMIN".card(id)
);

alter table "BGTMIN".card add constraint fk_card_language
foreign key(language_id)
references "BGTMIN"."language" (id);

create table "BGTMIN".game_card (
	id serial primary key,
	game_id INT not null,
	card_id INT not null,
	constraint fk_game_card_game
	foreign key(game_id)
	references "BGTMIN".game(id),
	constraint fk_game_card_card
	foreign key (card_id)
	references "BGTMIN".card(id)
);