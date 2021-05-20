package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/memberreactions")
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

    @Autowired
    private EntityManager entityManager;

    // Returns a list of all the member reactions

    @GetMapping(value = "/memberreactions", produces = "application/json")
    public ResponseEntity <?> findAllMemberReactions(){
        List <MemberReactions> allMemberReactions = memberReactionService.findAll();
        return new ResponseEntity<>(allMemberReactions, HttpStatus.OK);
    }

    // Returns one reaction based on the given id
    @GetMapping(value = "/memberreaction/{id}", produces = "application/json")
    public ResponseEntity <?> findMemberReactionById(@PathVariable Long id){
        MemberReactions memberReactions = memberReactionService.findMemberReactionById(id);
        return new ResponseEntity<>(memberReactions, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'CD', 'YDP')")
//    @PostMapping(value = "/memberreaction/submit")
//    public ResponseEntity <?> addReaction(
//            @RequestParam(value = "memberid") String memberid,
//            @RequestParam(value = "programid") Long programid,
//            @RequestParam(value = "clubid") Long clubid,
//            @RequestParam(value = "reaction") String reaction
//    )
//    {
//
//    }
}
//        var member = memberRepository.findMemberByMemberid(memberid).orElseThrow();
//        var clubprograms = clubService.
//
//
//        clubMembersRepository.save(new ClubMembers(clubRepository.findById(clubid).orElseThrow(), member));
//
//        Reactions mycurrentreaction;
//
//        mycurrentreaction = reactionRepository.findReactionsByReactionString(reaction).orElseThrow();
//
//        MemberReactions temp = new MemberReactions(member, mycurrentreaction, true, clubprograms);
//
//        memberReactionRepository.save(temp);
//
//        return new ResponseEntity<>(HttpStatus.OK);