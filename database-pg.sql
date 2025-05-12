CREATE TABLE categories (
    ca_id SERIAL,
    ca_name VARCHAR(35),
    PRIMARY KEY (ca_id)
);

CREATE TABLE languages (
    la_id SERIAL,
    la_name VARCHAR(10) NOT NULL,
    PRIMARY KEY (la_id);
);

CREATE TABLE books (
    bk_id          SERIAL,
    bk_uid         VARCHAR(36) NOT NULL, -- unikalny identyfikator książki
    bk_ca_id       INTEGER REFERENCES categories, -- kategoria np. ebook, manga, audiobook
    bk_main_title  VARCHAR(255),         -- główny tytuł (książka może mieć wiele tytułów)
    bk_la_id       INTEGER REFERENCES languages, -- język w jakim wydano książkę
    bk_cover       TEXT,                 -- plik graficzny zakodowany w Base64
    bk_description TEXT,                 -- opis
    bk_url         VARCHAR(256),         -- URL do strony z informacjami o książce
    bk_score       NUMBER(16, 2),        -- ocena
    bk_my_score    NUMBER(16, 2),        -- moja własna ocena
    bk_pages       NUMBER(16, 2),        -- liczba stron/rozdziałów (mangi mają rozdział np. 43.2)
    bk_progress    NUMBER(16, 2),        -- liczba przeczytanych stron/rozdziałów
    bk_st_id       INTEGER REFERENCES status, -- status wydanej książki

    PRIMARY KEY (bk_id)
);

CREATE UNIQUE INDEX books_idx001 ON books(bk_uid);
CREATE INDEX books_idx002 ON books(bk_la_id)

CREATE TABLE authors (
    au_id         SERIAL,
    au_last_name  VARCHAR(255), -- nazwisko
    au_first_name VARCHAR(255), -- imię
    PRIMARY KEY (au_id)
);

CREATE TABLE genres (
    ge_id   SERIAL,
    ge_name VARCHAR(30) NOT NULL,
    PRIMARY KEY (ge_id)
);

CREATE TABLE status (
    st_id   SERIAL,
    st_name VARCHAR(35), -- status np. "completed", "ongoing"
    PRIMARY KEY (st_id)
);

CREATE TABLE books_titles (
    bt_id    SERIAL,
    bt_bk_id INTEGER REFERENCES books,
    bt_title VARCHAR(255), -- tytuł książki
    PRIMARY KEY (bt_id)
);

CREATE INDEX books_titles_idx001 ON books_titles(bt_bk_id);

CREATE TABLE books_authors (
    ba_id    SERIAL,
    ba_bk_id INTEGER REFERENCES books,
    ba_au_id INTEGER REFERENCES authors,
    PRIMARY KEY (ba_id)
);

CREATE INDEX books_authors_idx001 ON books_authors(ba_bk_id);
CREATE INDEX books_authors_idx002 ON books_authors(ba_au_id);

CREATE TABLE books_genres (
    bg_id    SERIAL,
    bg_bk_id INTEGER REFERENCES books,
    bg_ge_id INTEGER REFERENCES genres,
    PRIMARY KEY (bg_id)
);

CREATE INDEX books_genres_idx001 ON books_genres(bg_bk_id);
CREATE INDEX books_genres_idx002 ON books_genres(bg_ge_id);

-- =============================================================================
insert into categories (ca_id, ca_name) values (1, 'ebook');
insert into categories (ca_id, ca_name) values (2, 'manga');
insert into languages (la_id, la_name) values (1, 'en');
insert into languages (la_id, la_name) values (2, 'pl');
insert into status (st_id, st_name) values (1, 'completed');
insert into status (st_id, st_name) values (2, 'ongoing');
insert into books (bk_id, bk_uid, bk_ca_id, bk_main_title, bk_la_id, bk_score, bk_my_score, bk_pages, bk_progress, bk_st_id) values (1, '53f45f1a-0b8d-4ed9-82b9-a592ef2bda84', 1, 'Cień Wiatru', 2, 9.2, 9.0, 512, 512, 1);
insert into books (bk_id, bk_uid, bk_ca_id, bk_main_title, bk_la_id, bk_score, bk_my_score, bk_pages, bk_progress, bk_st_id) values (2, 'f33e282a-e8f1-4b82-b184-293dc4731550', 1, 'Sto lat samotności', 2, 8.1, 7.0, 400, 68, 1);
insert into books (bk_id, bk_uid, bk_ca_id, bk_main_title, bk_la_id, bk_score, bk_my_score, bk_pages, bk_progress, bk_st_id) values (3, '2313017f-93cd-42e2-bcae-bdda05513283', 2, 'I Sold My Life for 10,000 Yen per Year', 1, 9.4, 9.0, 16.5, 16.5, 1);
insert into books_titles (bt_id, bt_bk_id, bt_title) values (1, 1, 'Cień Wiatru');
insert into books_titles (bt_id, bt_bk_id, bt_title) values (2, 2, 'Sto lat samotności');
insert into books_titles (bt_id, bt_bk_id, bt_title) values (3, 3, 'I Sold My Life for 10,000 Yen per Year');
insert into books_titles (bt_id, bt_bk_id, bt_title) values (4, 3, 'Three Days of Happiness');
insert into books_titles (bt_id, bt_bk_id, bt_title) values (5, 3, 'Mikkakan no Koufuku');
insert into authors (au_id, au_last_name) values (1, 'Carlos Ruiz Zafon');
insert into authors (au_id, au_last_name) values (2, 'Gabriel Garcia Marquez');
insert into authors (au_id, au_last_name) values (3, 'Miaki Sugaru');

select setval('categories_ca_id_seq', (select max(ca_id) from categories), true);
select setval('languages_la_id_seq', (select max(la_id) from languages), true);
select setval('status_st_id_seq', (select max(st_id) from status), true);
select setval('books_bk_id_seq', (select max(bk_id) from books), true);
select setval('authors_au_id_seq', (select max(au_id) from authors), true);

insert into books_authors (ba_bk_id, ba_au_id) values (1, 1);
INSERT INTO genres (ge_name) VALUES ('Literatura piękna');
INSERT INTO books_genres (bg_bk_id, bg_ge_id) VALUES (1, 1);
