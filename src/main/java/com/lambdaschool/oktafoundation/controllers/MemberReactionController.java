package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@Api (value = "/memberreactions")
public class MemberReactionController {

    @Autowired
    private ClubMembersRepository clubMembersRepository;

    @Autowired
    private MemberReactionService memberReactionService;

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubProgramRepository clubProgramRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    // Returns a list of all the member reactions
    @RequestMapping(value = "/memberreactions", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns all the members reactions",
        response = MemberReactions.class,
        responseContainer = "List")
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")

    public ResponseEntity<?> findAllMemberReactions() {
        List<MemberReactions> allMemberReactions = memberReactionService.findAll();
        return new ResponseEntity<>(allMemberReactions, HttpStatus.OK);
    }

    // Returns one reaction based on the given id
    @RequestMapping(value ="/memberreactions/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns the all the reactions with the path parameter for member id",
        response = MemberReactions.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found",
            response = MemberReactions.class),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    public ResponseEntity<?> findMemberReactionById(@PathVariable Long id) {
        MemberReactions memberReactions = memberReactionService.findMemberReactionById(id);
        return new ResponseEntity<>(memberReactions, HttpStatus.OK);
    }


    @RequestMapping(value = "/memberreaction/submit", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "adds a member reaction to a program")
    @ApiResponses(value = {
        @ApiResponse(code = 201,
            message = "Member reaction saved"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    public ResponseEntity<?> addNewReaction(
        @RequestParam(value = "mid") String mid,
        @RequestParam(value = "aid") Long aid,
        @RequestParam(value = "cid") Long cid,
        @RequestParam(value = "rx") String rx
    ) {

        var normallist = new HashMap<String, Integer>();
        normallist.put("1F601", 0);
        normallist.put("1F642", 0);
        normallist.put("1F610", 0);
        normallist.put("1F641", 0);
        normallist.put("1F61E", 0);


        Member member;
        var premember = memberRepository.findMemberByMemberid(mid);
        if (premember == null) {
            return new ResponseEntity<>("No such member", HttpStatus.NOT_FOUND);
        } else {
            member = premember;
        }


        var precp = clubProgramRepository.getClubProgramsByProgram_ProgramidAndClub_Clubid(
            aid, cid
        );

        ClubPrograms cp;
        if (precp.isEmpty()) {
            return new ResponseEntity<>("No such Club Activity", HttpStatus.NOT_FOUND);
        } else {
            cp = precp.get();
        }


        var checkInOut = Pattern.compile("check.?(in|out)$", Pattern.CASE_INSENSITIVE);
        var aname = cp.getProgram().getName();
        if (!checkInOut.matcher(aname).find()) {
            if (!normallist.containsKey(rx)) {
                return new ResponseEntity<>("This emoji can't be used in regular activity", HttpStatus.NOT_ACCEPTABLE);
            }

        }


        clubMembersRepository.save(new ClubMembers(clubRepository.findById(cid).orElseThrow(), member));

        Reactions currentreaction;
        var precurrentreaction = reactionRepository.findReactionByEmojiname(rx);
        if (precurrentreaction.isEmpty()) {
            return new ResponseEntity<>("No such emoji", HttpStatus.NOT_ACCEPTABLE);
        } else {
            currentreaction = precurrentreaction.get();
        }

        MemberReactions temp = new MemberReactions(member, currentreaction, cp);


        memberReactionRepository.save(temp);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}