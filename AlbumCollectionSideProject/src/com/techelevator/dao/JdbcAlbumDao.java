package com.techelevator.dao;
import com.techelevator.model.Album;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.Band;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.DataSource;

public class JdbcAlbumDao implements  AlbumDao {


    private JdbcTemplate jdbcTemplate;
    public JdbcAlbumDao (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public void JbdcBandDao (DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    @Override
    public int getAlbumId (String albumName){
        String sql = "SELECT album_id FROM album WHERE album_name LIKE ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, albumName);
    }

    @Override
    public Album getAlbum (int albumId){
        Album album = null;
        String sql = "SELECT band_name, album_name, record_label, release_date, runtime, genre_name, description " +
                     "FROM album JOIN genre ON album.genre_id = genre.genre_id JOIN band ON album.band_id = band.band_id " +
                     "WHERE album_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, albumId);
        if (result.next()){
            album = mapAlbumToRow(result);
        }
        return album;
    }

    @Override
    public Album getAlbumByAlbumName (String albumName){
        String sql = "SELECT album_name, band_name, record_label, release_date, runtime, genre_name, description " +
                "FROM album " +
                "JOIN band ON album.band_id = band.band_id " +
                "JOIN genre ON album.genre_id = genre.genre_id " +
                "WHERE album_name = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, albumName);
        if (result.next()){
            Album album = mapAlbumToRow(result);
            return album;
        }
        return null;
    }

    @Override
    public Album createAlbum(Album album) throws SQLException {

        String sql = "SELECT band_name FROM band WHERE band_name LIKE ?";
        String bandName = jdbcTemplate.queryForObject(sql, String.class, album.getBandName());
        sql = "SELECT band_id FROM band WHERE band_name LIKE ?";
        Integer bandId = jdbcTemplate.queryForObject(sql, Integer.class, bandName);
        sql = "INSERT INTO album (band_id, album_name, record_label, release_date, runtime, genre_id, description) VALUES" +
                "(?, ?, ?, ?, ?, (SELECT genre_id FROM genre WHERE genre_name LIKE ?), ?) RETURNING album_id;";
        int albumID = jdbcTemplate.queryForObject(sql, Integer.class, bandId, album.getAlbumName(), album.getRecordLabel(), album.getReleaseDate(), album.getRuntime(), album.getGenre(), album.getDescription());
        //INSERTING TRACKS
        for (int i = 0; i < album.getTrackList().length; i++) {
            String [] tracklist = album.getTrackList();
            String trackName = tracklist [i];
            sql = "INSERT INTO track (track_name, track_number, album_id) VALUES " +
                    "(?, ?, ?) RETURNING track_id;";
            int trackID = jdbcTemplate.queryForObject(sql, Integer.class, trackName, (i + 1), albumID );

       }
        System.out.println(album.getDisplay() + " was added!");
        return getAlbum(albumID);
    }

    // List Albums Methods
    @Override
    public List<Album> listAlbumsBand (String bandName){

        List<Album> albums = new ArrayList<>();
        String sql = "SELECT album_name, band_name, record_label, release_date, runtime, genre_name, description " +
                "FROM album " +
                "JOIN band ON album.band_id = band.band_id " +
                "JOIN genre ON album.genre_id = genre.genre_id " +
                "WHERE band_name = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, bandName);
        while (result.next()){
            Album album = mapAlbumToRow(result);
            albums.add(album);
            }
        return albums;
    }

    @Override
    public List<Album> listAlbumsMember (String memberName){
        //TODO Create Method
        return null;
    }

    @Override
    public List<Album> listAlbumsGenre (String genreName){
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT album_name, band_name, record_label, release_date, runtime, genre_name, description " +
                "FROM album " +
                "JOIN band ON album.band_id = band.band_id " +
                "JOIN genre ON album.genre_id = genre.genre_id " +
                "WHERE genre_name = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, genreName);
        while (result.next()){
            Album album = mapAlbumToRow(result);
            albums.add(album);
        }
        return albums;
    }

    @Override
   public List<Album> listAlbumsLabel (String recordLabel){
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT album_name, band_name, record_label, release_date, runtime, genre_name, description " +
                "FROM album " +
                "JOIN band ON album.band_id = band.band_id " +
                "JOIN genre ON album.genre_id = genre.genre_id " +
                "WHERE record_label = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, recordLabel);
        while (result.next()){
            Album album = mapAlbumToRow(result);
            albums.add(album);
        }
        return albums;
    }
    @Override
    public void updateAlbum (int albumId){
        //TODO Create Method
    }

   private Album mapAlbumToRow (SqlRowSet rowSet){
        Album album = new Album();

        album.setBandName(rowSet.getString("band_name"));
        album.setName(rowSet.getString("album_name"));
        album.setRecordLabel(rowSet.getString("record_label"));
        album.setReleaseDate(rowSet.getString("release_date"));
        album.setRuntime(rowSet.getDouble("runtime"));
        album.setGenre(rowSet.getString("genre_name"));
        album.setDescription(rowSet.getString("description"));
        return album;
    }
}
