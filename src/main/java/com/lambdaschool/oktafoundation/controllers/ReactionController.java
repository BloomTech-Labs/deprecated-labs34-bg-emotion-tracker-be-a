package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Reactions;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.services.ReactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/reactions")
public class ReactionController {
    @Autowired
    ReactionRepository reactionrepos;

    @Autowired
    private ReactionsService reactionsService;

    @GetMapping (value = "/reactions",
            produces = "appilacation/json")
    public ResponseEntity<?> listAllReactions(){
        List<Reactions> myReactions = reactionsService.findAll();
        return new ResponseEntity<>(myReactions, HttpStatus.OK);
    }
}
