package com.techelevator;

import com.techelevator.dao.JbdcBandDao;
import com.techelevator.dao.JdbcAlbumDao;
import com.techelevator.dao.JdbcBandMemberDao;
import com.techelevator.model.Album;
import com.techelevator.model.Band;
import com.techelevator.model.BandMember;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sound.midi.Soundbank;
import javax.swing.text.html.parser.Parser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    static Scanner inputScanner = new Scanner(System.in);
    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        JdbcAlbumDao jdbcAlbum = new JdbcAlbumDao(dataSource);
        JbdcBandDao jdbcBand = new JbdcBandDao(dataSource);
        JdbcBandMemberDao jdbcBandMember = new JdbcBandMemberDao(dataSource);
        CLI application = new CLI(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);


        dataSource.setUrl("jdbc:postgresql://localhost:5432/RecordCollectionTest");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        application.displayBanner();
        boolean loop = true;
        while (loop) {
            application.displayMainMenu();
            int selection = application.prompt();

            /////ADD ALBUM
            if (selection == 1) {
                Album newAlbum = application.addAlbumPrompt();
                String bandName = newAlbum.getBandName();
                String sql = "select COUNT(band_id) FROM band WHERE band_name LIKE ?;";
                Integer bandCount = jdbcTemplate.queryForObject(sql, Integer.class, bandName);

                if (bandCount != null && bandCount > 0){
                    //TODO Print out band and members to see if anyone needs added write a get band ID method to be able to add that to band_member table
                    Band band = jdbcBand.getMemberList(bandName);
                    int bandId = jdbcBand.getBandId(bandName);
                    System.out.println("*************************************");
                    band.printBandMembers();
                    System.out.println("*************************************");
                    boolean isRunning = true;

                    while (isRunning == true) {

                                System.out.println("Would you like to add any additional members to the band? (Please enter Yes or No)");
                                String answer = inputScanner.nextLine();
                                if (answer.equalsIgnoreCase("yes")) {
                                    System.out.println("Please enter member name one at a time:");
                                    String memberName = inputScanner.nextLine();
                                    if (jdbcBandMember.doesMemberExist(memberName) != null){
                                        System.out.println("It looks like " + memberName + " already exists, is he a repeat?");
                                            String input = inputScanner.nextLine();
                                                if (input.equalsIgnoreCase("yes")){
                                                   int repeatMemberId= jdbcBandMember.doesMemberExist(memberName).getMemberId();
                                                    jdbcBandMember.addMemberToBand(bandId,repeatMemberId);
                                                }
                                                else if (input.equalsIgnoreCase("no")){
                                                    //TODO CREATE ADD MEMBER
                                                }

                                    }



                                    } else if (answer.equalsIgnoreCase("no")) {
                                        isRunning = false;
                                    }
                        }
                    jdbcAlbum.createAlbum(newAlbum);
                    } else {
                        //TODO write a method to ask for members
//                        jdbcBand.createBand(bandName);
                        jdbcAlbum.createAlbum(newAlbum);
                }
            }

            //List Albums
            if (selection == 2){
                application.listAlbumMenu();
                selection = application.prompt();

                // 1. BY ALBUM NAME
                 if (selection == 1){
                    System.out.println("Please input 'Album name'");
                    String albumName = inputScanner.nextLine();
                    Album album = jdbcAlbum.getAlbumByAlbumName(albumName);
                     System.out.println(album.getDisplay());
                    album.printAlbum(album);
                 }

                 // 2. By Artist Name
                if (selection == 2) {

                    System.out.println("Please input: 'Artist Name'");
                    String artistName = inputScanner.nextLine();
                    List <Album> albums = jdbcAlbum.listAlbumsBand(artistName);
                        for (int i = 0 ; i < albums.size(); i++){
                            System.out.println(albums.get(i).getDisplay());
                        }
                }

                // 3. By Member
                if (selection == 3){
                    System.out.println("Not Done!");
//                    System.out.println("Please Insert Band Name:");
//                    String bandName = inputScanner.nextLine();
////                    Band band = jdbcBand.getMemberList(bandName);
////                    band.printBandMembers(band);


                }

                // 4. Genre
                if (selection == 4){
                    selection = 2; // TO KEEP WHILE LOOP GOING
                    System.out.println("Please input: 'Genre Name'");
                    String artistName = inputScanner.nextLine();
                    List <Album> albums = jdbcAlbum.listAlbumsGenre(artistName);
                        for (int i = 0 ; i < albums.size(); i++){
                            System.out.println(albums.get(i).getDisplay());
                        }

                }
                // 5. By Record Label
                if (selection == 5){
                    System.out.println("Please input: 'Label Name'");
                    String labelName = inputScanner.nextLine();
                    List <Album> albums = jdbcAlbum.listAlbumsLabel(labelName);
                        for (int i = 0 ; i < albums.size(); i++){
                            System.out.println(albums.get(i).getDisplay());
                        }
                }

            }

            //UPDATE ALBUM
            if (selection == 3){
                //TODO Make Update Methods
                System.out.println("Working on it");
            }

            //BAND MENU
            if (selection == 4){
                //TODO Make Band Menu prompt
                System.out.println("Working on it");

            }

            if (selection == 5){
                 System.out.println("BYE!");
                loop = false;
            }
        }
    }

    // Methods
    private void displayBanner() {
        System.out.println("****************************************");
        System.out.println("****Welcome To My Record Collection ****");
        System.out.println("****************************************");
        System.out.println();
        System.out.println();
    }
    private void displayMainMenu (){
        System.out.println("1. Add Album");
        System.out.println("2. List Albums");
        System.out.println("3. Update Album");
        System.out.println("4. Band Menu");
        System.out.println("5. Exit");
    }

    private void listAlbumMenu(){
        System.out.println( "1. Album Name");
        System.out.println("2. Artist");
        System.out.println("3. Member");
        System.out.println("4. Genre");
        System.out.println("5. Record Label");
        System.out.println("6. Exit");
    }
    private int prompt() {
        System.out.println("Please select an option:");
        String userInput = inputScanner.nextLine();
        int answer = 0;
        answer = Integer.parseInt(userInput);
        if (answer >= 0 && answer < 7 ){
        return answer;
        }
          else {
                System.out.println("Please enter a valid number!");
            }
        return answer;
    }

    private Album addAlbumPrompt(){

            System.out.println("Please Enter:");
            System.out.println("Album Name:");
            String albumName = inputScanner.nextLine().trim();
            System.out.println("Band/Artist Name:");
            String bandName = inputScanner.nextLine().trim();

            System.out.println("Genre:");
            System.out.println("Rock || Pop || Punk || Jazz || Rap || R&B || Experimental || Electronic || Other");
            String genreName = inputScanner.nextLine();
            System.out.println("Track List:");
            System.out.println("Please enter the number of tracks:");
            String numberOfTracks = inputScanner.nextLine().trim();
            System.out.println();

            //Tracklist
            String[] newTrackList = new String[Integer.parseInt(numberOfTracks)];
            boolean infiniteLoop = true;
            int i =0;
            while (infiniteLoop) {
                for (i = 0; i <= newTrackList.length; i++) {
                    if (i == 0) {
                        System.out.println("Please enter the first track:");
                        String newTrackName = inputScanner.nextLine();
                        newTrackList[i] = newTrackName;
                    } else if (i < newTrackList.length - 1) {
                        System.out.println("Please enter the next track:");
                        String newTrackName = inputScanner.nextLine();
                        newTrackList[i] = newTrackName;
                    } else if (i == newTrackList.length - 1) {
                        System.out.println("Please enter the last track:");
                        String newTrackName = inputScanner.nextLine();
                        newTrackList[i] = newTrackName;
                    }
                    if (i == newTrackList.length){
                        infiniteLoop = false;
                }
                }
            }
            inputScanner.nextLine();
            System.out.println("Record Label:");
            String recordLabel = inputScanner.nextLine().trim();
            System.out.println("Release Date:");
            String releaseDate = inputScanner.nextLine().trim();
            System.out.println("Runtime(in minutes):");
            double runtime = inputScanner.nextDouble();
            inputScanner.nextLine();
            System.out.println("Description:");
            String description = inputScanner.nextLine().trim();

        Album newAlbum = new Album(albumName, bandName, genreName, newTrackList, releaseDate, runtime,description,recordLabel);
       return newAlbum;
    }
    // Constructor
    public CLI(BasicDataSource dataSource){}
}
