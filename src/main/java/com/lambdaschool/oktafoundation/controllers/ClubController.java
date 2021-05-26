package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api (value = "/clubs")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;

    @RequestMapping(value = "/clubs", method = RequestMethod.GET)
    @ApiOperation(value = "returns a list of all clubs",
        response = Club.class,
        responseContainer = "List")
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    public ResponseEntity<?> listAllClubs() {
        List<Club> myClubs = clubService.findAll();
        return new ResponseEntity<>(myClubs, HttpStatus.OK);
    }
}