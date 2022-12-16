package com.techelevator.dao;

import com.techelevator.model.Album;

import java.sql.SQLException;
import java.util.List;

public interface AlbumDao {

    Album createAlbum (Album album) throws SQLException;

    // List Albums Methods
    public int getAlbumId (String albumName);
    Album getAlbum (int AlbumId);
    public Album getAlbumByAlbumName (String albumName);
    List<Album> listAlbumsBand (String bandName);
    List<Album> listAlbumsMember (String memberName);
    List<Album> listAlbumsGenre (String genreName);
    List<Album> listAlbumsLabel (String recordLabel);
    void updateAlbum(int albumId);

}
