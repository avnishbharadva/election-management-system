package com.ems.entities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VoterIdGenerator {

    private static JdbcTemplate jdbcTemplate;

    public VoterIdGenerator(JdbcTemplate jdbcTemplate){
        VoterIdGenerator.jdbcTemplate = jdbcTemplate;
    }

    public static String getNextId(){
        jdbcTemplate.update("insert into voter_sequence values()");

        Integer nextId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        String voterId = String.format("%09d", nextId);
        log.info("VoterIdGenerator created ID : {}",voterId);
        return voterId;
    }
}
