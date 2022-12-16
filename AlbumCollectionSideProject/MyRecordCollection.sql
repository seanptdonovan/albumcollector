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
    release_date date,
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
    CONSTRAINT PK_track PRIMARY KEY (track_id)
);

CREATE TABLE track_album (
    track_id int NOT NULL,
    album_id int NOT NULL,
    CONSTRAINT PK_track_album PRIMARY KEY (track_id, album_id),
    CONSTRAINT FK_track_album_track FOREIGN KEY (track_id) REFERENCES track(track_id),
    CONSTRAINT FK_track_album_album FOREIGN KEY (album_id) REFERENCES album(album_id)
);

COMMIT;