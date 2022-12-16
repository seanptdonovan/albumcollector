package com.techelevator.model;
public class Album {

    //Properties
    private String name;
    private String releaseDate;
    private String genre;
    private String [] trackList;
    private String description;
    private double runtime;
    private String bandName;
    private String recordLabel;

    // album artwork, ratings, etc...

    //Get and Setters

    public String getDisplay(){
        return getAlbumName() + " by " + getBandName();
    }
    public String getAlbumName(){
        return name;
    }
    public String getBandName(){return bandName; }
    public String getReleaseDate(){
        return releaseDate;
    }
    public String getGenre(){
        return genre;
    }
    public String [] getTrackList(){
        return trackList;
    }
    public double getRuntime(){
        return runtime;
    }
    public String getDescription (){
        return description;
    }
    public String getRecordLabel(){
        return recordLabel;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBandName(String bandName){
        this.bandName = bandName;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setRecordLabel(String recordLabel){
        this.recordLabel = recordLabel;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //Constructor

    public Album(String albumName){
        this.name = albumName;
    }


    public Album(String albumName, String bandName, String genre, String[] trackList, String releaseDate, double runtime, String description, String recordLabel){
        this.name = albumName;
        this.bandName = bandName;
        this.genre = genre;
        this.trackList = trackList;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.description = description;
        this.recordLabel = recordLabel;
    }
    public Album(){}
    //Methods
    public void printAlbum(Album album){
        System.out.println();
        System.out.println("********************");
        System.out.println(album.getDisplay());
        System.out.println("Record Label: " + album.getRecordLabel());
        System.out.println("Genre: "+album.getGenre());
        System.out.println("Release Date: " + album.getReleaseDate());
        System.out.println("Runtime: " + album.getRuntime());
        System.out.println("Description: " + album.getDescription());
    }
    public void printTrackList(Album album ) {
        String[] tracklist = album.getTrackList();
        for (int i = 0; i < tracklist.length; i++) {
            String track = tracklist[i];
            System.out.println(track);
        }

    }
}
