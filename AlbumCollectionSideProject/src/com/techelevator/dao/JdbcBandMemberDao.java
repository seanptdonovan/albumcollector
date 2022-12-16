package com.techelevator.dao;

import com.techelevator.model.BandMember;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JdbcBandMemberDao implements BandMemberDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcBandMemberDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createNewMember(String memberName){

    }

    @Override
    public boolean addMemberToBand(int bandId, int memberId){

        String sql = "INSERT INTO band_member (band_id, member_id) VALUES (?, ?);";
        boolean addedMember = Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, boolean.class, bandId, memberId));


        return true;
    }

    @Override
    public BandMember doesMemberExist(String bandMember){

        List<String> memberList = new ArrayList<>();

            String sql = "SELECT member_name " +
                    "FROM member;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                String member = (results.getString("member_name"));
                memberList.add(member);
            }
            if (memberList == null){
                return null;
            }
            for(String member : memberList) {
                if (Objects.equals(member, bandMember)){
                    sql = " SELECT member_id FROM member WHERE member_name = ?;";
                    int returnedMemberId = jdbcTemplate.queryForObject(sql, Integer.class, bandMember);
                    BandMember returnedMember = new BandMember(bandMember);
                    returnedMember.setMemberId(returnedMemberId);

                    return returnedMember;
                }
            }
            return null;
        }

}
