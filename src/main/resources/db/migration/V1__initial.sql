CREATE TABLE game (
	id serial PRIMARY KEY,
	title text not null,
	description text,
	language_id INT not null,
	created_at TIMESTAMP NOT null default NOW()
);

CREATE TABLE game_translation (
	id serial PRIMARY KEY,
	"title" text not null,
	"description" text,
	game_id INT not null,
	language_id INT not null,
	created_at TIMESTAMP NOT null default NOW(),
	constraint fk_game
	foreign key(game_id)
	references game(id)
	on delete cascade
);


CREATE TABLE "language" (
	id serial PRIMARY KEY,
	"name" text not null,
	code text not null,
	created_at TIMESTAMP NOT null default NOW()
);

alter table game_translation add constraint fk_game_translation_language
foreign key(language_id)
references "language" (id);

alter table game add constraint fk_game_language
foreign key(language_id)
references "language" (id);

CREATE TABLE card (
	id serial PRIMARY KEY,
	title text not null,
	description text,
	"language_id" INT not null,
	created_at TIMESTAMP NOT null default NOW()
);

CREATE TABLE card_translation (
	id serial PRIMARY KEY,
	"title" text not null,
	"description" text,
	card_id INT not null,
	language_id INT not null,
	created_at TIMESTAMP NOT null default NOW(),
	constraint fk_card
	foreign key(card_id)
	references card(id)
	on delete cascade
);

alter table card_translation add constraint fk_card_translation_language
foreign key(language_id)
references "language" (id);

alter table card add constraint fk_card_language
foreign key(language_id)
references "language" (id);

create table game_card (
	id serial primary key,
	game_id INT not null,
	card_id INT not null,
	constraint fk_game_card_game
	foreign key(game_id)
	references game(id),
	constraint fk_game_card_card
	foreign key (card_id)
	references card(id)
);