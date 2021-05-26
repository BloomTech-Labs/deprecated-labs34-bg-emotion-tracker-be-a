package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberService;
import com.lambdaschool.oktafoundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClubMembersRepository clubMembersRepository;

    @Autowired
    private MemberService memberService;

    /**
     * Returns a list of all clubs
     * Example: "http://localhost:2019/clubs/clubs
     * @return JSON list of all Clubs with status of OK
     * @see ClubService findAll()
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @GetMapping(value = "/clubs",
        produces = "application/json")
    public ResponseEntity<?> listAllClubs() {
        List<Club> myClubs = clubService.findAll();
        return new ResponseEntity<>(myClubs, HttpStatus.OK);
    }

    /**
     * Returns the Club with the given id
     * Example: http://localhost:2019/clubs/club/20
     *
     * @param clubid The primary key of the club
     * @return JSON object of the Club
     * @see ClubService#findClubById(Long) ClubService.findClubById(long)
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @GetMapping(value = "/club/{clubid}", produces = "application/json")
    public ResponseEntity<?> getClubById(@PathVariable Long clubid)
    {
        Club c = clubService.findClubById(clubid);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    /**
     * Given a complete Club object, creates a new Club.
     * <br> Example: <a href="http://localhost:2019/clubs/newClub"
     *
     * @param club A complete new Club to be added
     * @return A location header with the URI to the newly created Club and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating location header
     * @see ClubService#save(Club)
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @PostMapping(value = "club/newClub", consumes = "application/json")
    public ResponseEntity<?> addNewClub(
            @Valid @RequestBody Club club) throws URISyntaxException
    {
        club.setClubid(0);
        club = clubService.save(club);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newClubURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{clubid}")
                .buildAndExpand(club.getClubid())
                .toUri();
        responseHeaders.setLocation(newClubURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);



    }


}