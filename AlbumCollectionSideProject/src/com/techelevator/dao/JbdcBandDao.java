package com.techelevator.dao;

import com.techelevator.model.Band;
import com.techelevator.model.BandMember;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import javax.sql.DataSource;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JbdcBandDao implements BandDao {

    private JdbcTemplate jdbcTemplate;
    public JbdcBandDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getBandId (String bandName){

        String sql = "SELECT band_id FROM band WHERE band_name = ?";

        int bandId = jdbcTemplate.queryForObject(sql, Integer.class, bandName);
        return bandId;
    }

@Override
    public Band getBandById (int bandId){
        Band  band = null;
        String sql = "SELECT band_name FROM band WHERE band_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, bandId);
        if (result.next()){
            band = mapBandToRow(result);
        }
        String printBand = band.getBandName();
        System.out.println(printBand);
        return band;
    }

    @Override
    public Band getBandByName (String bandName){
        Band  band = null;
        String sql = "SELECT band_name FROM band WHERE band_name = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, bandName);
        if (result.next()){
            band = mapBandToRow(result);
        }
        assert band != null;
        String printBand = band.getBandName();
        System.out.println(printBand);
        return band;
    }

    @Override
    public Band createBand(String bandName, List<BandMember> memberList){
        String sql = "INSERT INTO band (band_name) VALUES (?) RETURNING band_id;";
        Integer  bandId = jdbcTemplate.queryForObject(sql, Integer.class, bandName, memberList);

        for(BandMember member : memberList){
            sql = "INSERT INTO member (member_name) VALUES (?) RETURNING member_id;";
            int memberId = jdbcTemplate.queryForObject(sql,Integer.class, member);
            sql = "INSERT INTO band_member (band_id, member_id) VALUES (?, ?);";
            jdbcTemplate.queryForObject(sql, Integer.class, bandId, memberId);
        }

        return getBandById(bandId);
    }

    @Override
    public Band  getMemberList(String bandName){
        List <BandMember> memberList = new ArrayList<>();
        Band band = new Band(bandName);

        String sql = "SELECT member_name " +
                "FROM member " +
                "JOIN band_member ON member.member_id = band_member.member_id " +
                "JOIN band ON band_member.band_id = band.band_id " +
                "WHERE band_name LIKE ?;";
       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bandName);
        while (results.next()){
            String member = mapBandMemberToRow(results);
            BandMember bandMember = new BandMember(member);
           memberList.add(bandMember);
        }
        band.setBandMembers(memberList);
      return band;
    }



    private Band mapBandToRow (SqlRowSet rowSet){
        Band band = new Band();
        band.setName(rowSet.getString("band_name"));
        return band;
    }

    private String  mapBandMemberToRow (SqlRowSet rowSet){
        String member;
        member = (rowSet.getString("member_name"));
        return member;
    }

}
