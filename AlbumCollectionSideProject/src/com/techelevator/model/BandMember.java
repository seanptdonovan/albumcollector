package com.techelevator.model;

import java.util.List;

public class BandMember {
    // Variables
    private String name;
    private List<Album> albumsPlayedOn;
    private int memberId;


    // Getters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    // Constructor
    public BandMember(String name){
        this.name = name;
    }
    public BandMember(){}



    // Method
    public void getBandMember(){}
    public int getMemberId(){
        return memberId;
    }
    public void setMemberId(int memberId){
        this.memberId = memberId;
    }

}
