package com.techelevator.model;

import java.util.List;

public class Band {


    //Variables
    private String name;
    private List <BandMember> bandMember;

    // Getters and Setters

    public String getBandName(){
        return name;
    }

    public void setName(String name){
        name = this.name;
    }
    public List <BandMember> getBandMember (){
        return bandMember;
    }
    public void setBandMembers(List <BandMember> bandMembers) {
        bandMember = bandMembers;
    }

    //Constructor

    public Band(String bandName){
        name = bandName;
    }
    public Band () {}

    //Method

    public void printBandMembers(){
        List <BandMember> memberList = getBandMember();
        for (BandMember member : memberList){
            System.out.println(member.getName());
        }

    }





}
