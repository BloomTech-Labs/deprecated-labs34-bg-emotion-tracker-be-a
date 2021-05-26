package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubMembers;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ClubService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Api (value = "/clubs")
public class ClubController {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;


    @RequestMapping(value = "/clubs", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a list of all clubs",
        response = Club.class,
        responseContainer = "List")
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
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

    /**
     * Updates the Club record with the given id using the provided data
     * <br> Example: <a href="http://localhost:2019/clubs/club/22">http://localhost:2019/clubs/club/22</a>
     *
     * @param club An object containing values for just the fields being updated, all other fields left NULL
     * @param clubid The primary key of the Club you wish to replace
     * @return status of OK
     * @see ClubService#save(Club) ClubService.save(Club)
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @PatchMapping(value = "/club/{clubid}",
    consumes = "application/json")
    public ResponseEntity<?> updateClub(@RequestBody
                                        Club club, @PathVariable long clubid)
    {
        clubService.update(club,
                clubid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given a Club key and memberID, add the member to the club.
     * <br> Example: <a href="http://localhost:2019/club/22/addMember/testmember2">http://localhost:2019/club/22/addMember/testmember2</a>
     *
     * @param cid The primary key of the club
     * @param mid The String value of the memberID
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @PostMapping(value = "/club/{cid}/addMember/{mid}")
    public ResponseEntity<?> addNewMemberToClub(@PathVariable Long cid, @PathVariable String mid){
        var club = clubService.findClubById(cid);
        var member = memberService.findMemberByStringId(mid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given a Club key and a body supplying a list of memberids, add the members to the clubs.
     *
     * @param cid The primary key of the club
     * @param members The body supplying a list of memberid
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @PostMapping(value = "/club/{cid}/addMembers")
    public ResponseEntity<?> addNewMembersToClub(@PathVariable long cid, @RequestBody List<Member> members){
        var club = clubService.findClubById(cid);

        for (var i: members) {
            var mem = memberService.findMemberByStringId(i.getMemberid());
            clubMembersRepository.save(new ClubMembers(club, mem));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given a Club key and memberID, remove the member from the club if the member is found in.
     * <br> Example: <a href="http://localhost:2019/club/22/removeMember/testmember2">http://localhost:2019/club/22/removeMember/testmember2</a>
     *
     * @param cid The primary key of the club
     * @param mid The String value of the memberID
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @DeleteMapping(value = "/club/{cid}/removeMember/{mid}")
    public ResponseEntity<?> removeMemberFromClub(@PathVariable Long cid, @PathVariable String mid) throws URISyntaxException
    {
        var cm = clubMembersRepository.findClubMembersByClub_ClubidAndMemberId_Memberid(cid, mid);
        if(cm.isPresent()){
            clubMembersRepository.delete(cm.get());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Such Relationship exists", HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * Given a Club key and a body supplying a list of memberids, remove the members
     *
     * @param cid The primary key of the club
     * @param members The body supplying a list of memberid
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @PostMapping(value = "/club/{cid}/removeMembers")
    public ResponseEntity<?> removeMembersFromClub(@PathVariable Long cid, @RequestBody List<Member> members){
        var club = clubService.findClubById(cid);

        for(var i: members){
            var mem = memberService.findMemberByStringId(i.getMemberid());
            var cm = clubMembersRepository.findClubMembersByClub_ClubidAndMemberId_Memberid(club.getClubid(), mem.getMemberid());
            cm.ifPresent(clubMembers -> clubMembersRepository.delete(clubMembers));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}