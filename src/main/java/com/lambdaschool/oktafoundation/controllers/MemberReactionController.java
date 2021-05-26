package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping ("/memberreactions")
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

    @GetMapping(value = "/memberreactions",
        produces = "application/json")
    public ResponseEntity<?> findAllMemberReactions() {
        List<MemberReactions> allMemberReactions = memberReactionService.findAll();
        return new ResponseEntity<>(allMemberReactions, HttpStatus.OK);
    }

    // Returns one reaction based on the given id
    @GetMapping (value = "/memberreaction/{id}",
        produces = "application/json")
    public ResponseEntity<?> findMemberReactionById(@PathVariable Long id) {
        MemberReactions memberReactions = memberReactionService.findMemberReactionById(id);
        return new ResponseEntity<>(memberReactions, HttpStatus.OK);
    }

//    @PostMapping(value = "/memberreaction/submit",
//        consumes = "application/json")
//    public ResponseEntity<?> addMemberReaction(@RequestBody MemberReactions newReaction)
//    {
//        newReaction.setMemberreactionid(0);
//        memberReactionService.save(newReaction);
//
//        return new ResponseEntity<>(newReaction, HttpStatus.CREATED);
//    }

    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @PostMapping(value = "/memberreaction/submit")
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

        var premember = memberRepository.findMemberByMemberid(mid);
        Member member;
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