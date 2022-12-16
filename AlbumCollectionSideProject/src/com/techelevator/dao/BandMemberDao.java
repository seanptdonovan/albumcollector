package com.techelevator.dao;

import com.techelevator.model.BandMember;

public interface BandMemberDao {

    public void createNewMember(String memberName);
    public BandMember doesMemberExist(String bandMember);
    public boolean addMemberToBand(int bandId, int memberId);
}
