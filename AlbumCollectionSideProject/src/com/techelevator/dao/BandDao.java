package com.techelevator.dao;

import com.techelevator.model.Band;
import com.techelevator.model.BandMember;

import java.sql.SQLException;
import java.util.List;

public interface BandDao {

    Band getBandById (int bandId);
    Band getBandByName (String bandName);
    Band createBand (String bandName, List<BandMember> memberList);
    Band getMemberList(String bandName);
    int getBandId (String bandName);

}
