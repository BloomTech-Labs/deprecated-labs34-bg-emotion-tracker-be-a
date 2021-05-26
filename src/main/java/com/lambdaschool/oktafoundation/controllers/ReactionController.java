package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Reactions;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.services.ReactionsService;
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
@Api (value ="/reactions")
public class ReactionController {
    @Autowired
    private ReactionRepository reactionrepos;

    @Autowired
    private ReactionsService reactionsService;

    @RequestMapping(value = "/reactions", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns all emojis",
        response = Reactions.class,
        responseContainer = "List")
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    public ResponseEntity<?> listAllReactions(){
        List<Reactions> myReactions = reactionsService.findAll();
        return new ResponseEntity<>(myReactions, HttpStatus.OK);
    }
}
