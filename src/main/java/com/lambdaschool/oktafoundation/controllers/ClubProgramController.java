package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubPrograms;
import com.lambdaschool.oktafoundation.services.ClubProgramService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clubprograms")
public class ClubProgramController {

    @Autowired
    private ClubProgramService clubProgramService;

    /**
     * Returns a list of all clubprograms
     *
     * @return JSON list of all clubprograms with a status of OK
     */
    @ApiOperation(value = "Returns all club activities",
            response = ClubPrograms.class,
    responseContainer = "List")
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @GetMapping(value = "/clubprograms", produces = "application/json")
    public ResponseEntity<?> listAllClubPrograms(){
        List<ClubPrograms> allClubPrograms = clubProgramService.findAll();
        return new ResponseEntity<>(allClubPrograms, HttpStatus.OK);
    }

    /**
     * Returns a single clubprogram based off a clubprogram id number
     * Example: http://localhost:2019/clubprograms/clubprogram/5
     *
     * @param clubprogramid The primary key of the clubprogram
     * @return JSON object of the clubprogram
     * @see ClubProgramService.findClubProgramById(Long)
     */
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @GetMapping(value = "/clubprogram/{clubprogramid}", produces = "application/json")
//    public ResponseEntity<?> getClubProgramById(@PathVariable Long clubprogramid){
//        ClubPrograms cp = ClubProgramService.findClubProgramById(clubprogramid);
//        return new ResponseEntity<>(cp, HttpStatus.OK);
//
//    }


}
