BEGIN TRANSACTION;

DROP TABLE IF EXISTS album, band, band_member, member, genre, track, track_album CASCADE; 

CREATE TABLE band (
    band_id serial,
    band_name varchar(50),
    CONSTRAINT PK_band PRIMARY KEY(band_id)
);

CREATE TABLE genre (
    genre_id serial,
    genre_name varchar (25),
    CONSTRAINT PK_genre PRIMARY KEY (genre_id)
);

--insert genre names
INSERT INTO genre (genre_name) VALUES ('Rock'),('Pop'),('Punk'),('Jazz'), ('Rap'), ('R&B'),('Experimental'),('Electronic'),('Other');

CREATE TABLE album (
    album_id serial,
    band_id int,
    album_name varchar(50)  NOT NULL,
    record_label varchar(40),
    release_date varchar(20),
    runtime int NOT NULL,
    genre_id int,
    description varchar(300),
    CONSTRAINT PK_album PRIMARY KEY(album_id),
    CONSTRAINT FK_band FOREIGN KEY(band_id) REFERENCES band(band_id),
    CONSTRAINT FK_album_genre FOREIGN KEY(genre_id) REFERENCES genre(genre_id)
);

CREATE TABLE member (
    member_id serial,
    member_name varchar(50)  NOT NULL,
    CONSTRAINT PK_member PRIMARY KEY(member_id)
);

CREATE TABLE band_member(
    band_id int NOT NULL,
    member_id int NOT NULL,
    CONSTRAINT PK_band_member PRIMARY KEY (band_id, member_id),
    CONSTRAINT FK_band_member_band FOREIGN KEY (band_id) REFERENCES band(band_id),
    CONSTRAINT FK_band_member_member FOREIGN KEY (member_id) REFERENCES member(member_id)
);


CREATE TABLE track (
    track_id serial,
    track_name varchar (100),
    track_number int,
    album_id int,
    CONSTRAINT PK_track PRIMARY KEY (track_id),
    CONSTRAINT FK_track FOREIGN KEY (album_id) REFERENCES album(album_id)
);


-- Test insert data
INSERT INTO band (band_name) VALUES ('The Testers');
INSERT INTO album (band_id, album_name, record_label, release_date, runtime, genre_id, description) VALUES (1, 'Test', 'Atlantic', '1991-10-17', '44', 1,'yooooooooo');
INSERT INTO member (member_name) VALUES ('Test Member');
INSERT INTO band_member (band_id, member_id) VALUES (1, 1);
INSERT INTO track (track_name, track_number) VALUES('Test Track', 1);


INSERT INTO band (band_name) VALUES ('Bologna Gang');
INSERT INTO album (band_id, album_name, record_label, release_date, runtime, genre_id, description) 
VALUES ((SELECT band_id FROM band WHERE band_name LIKE 'Bologna Gang'), 'Tupac is Alive', 'Self-released', '2012-10-11', '10', (SELECT genre_id FROM genre WHERE genre_name LIKE 'Punk'),'Three dudes who look the same');
INSERT INTO member (member_name) VALUES ('Alex French'),('Drew Vaccaro'), ('Sean Donovan');
INSERT INTO band_member (band_id, member_id) VALUES 
((SELECT band_id FROM band WHERE band_name LIKE 'Bologna Gang'), (SELECT member_id FROM member WHERE member_name LIKE 'Alex French')),
((SELECT band_id FROM band WHERE band_name LIKE 'Bologna Gang'),(SELECT member_id FROM member WHERE member_name LIKE 'Drew Vaccaro')),
((SELECT band_id FROM band WHERE band_name LIKE 'Bologna Gang'),(SELECT member_id FROM member WHERE member_name LIKE 'Sean Donovan'));
INSERT INTO track (track_name, track_number) VALUES('Vegan Straight Edge', 9);
INSERT INTO track (track_name, track_number) VALUES('Knifed in the Guts', 1);
INSERT INTO track (track_name, track_number) VALUES('Loud Talker', 4);


INSERT INTO band (band_name) VALUES ('Ahn');
INSERT INTO album (band_id, album_name, record_label, release_date, runtime, genre_id, description) 
VALUES ((SELECT band_id FROM band WHERE band_name LIKE 'Ahn'), 'Melon', 'Vitreous China', '2022-06-01', '90', (SELECT genre_id FROM genre WHERE genre_name LIKE 'Experimental'),'Three dudes making a racket');
INSERT INTO member (member_name) VALUES ('Bryce Wood'),('Kevin Greulich');
INSERT INTO band_member (band_id, member_id) VALUES 
((SELECT band_id FROM band WHERE band_name LIKE 'Ahn'), (SELECT member_id FROM member WHERE member_name LIKE 'Bryce Wood')),
((SELECT band_id FROM band WHERE band_name LIKE 'Ahn'),(SELECT member_id FROM member WHERE member_name LIKE 'Kevin Greulich')),
((SELECT band_id FROM band WHERE band_name LIKE 'Ahn'),(SELECT member_id FROM member WHERE member_name LIKE 'Sean Donovan'));
INSERT INTO track (track_name, track_number) VALUES('Orange Melon Meat Side A', 1);
INSERT INTO track (track_name, track_number) VALUES('Orange Melon Meat Side B', 2);



COMMIT;