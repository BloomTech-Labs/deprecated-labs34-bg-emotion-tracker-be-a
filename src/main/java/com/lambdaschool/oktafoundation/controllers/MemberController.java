package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ErrorDetail;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.services.MemberService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

//@Api annotations added to generate custom swagger documentation

@RestController
@Api(value = "members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/members", method = RequestMethod.GET, produces = "application/json")

    @ApiOperation(value = "returns all Members",
        response = Member.class,
        responseContainer = "List")
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> listAllMembers() {
        List<Member> myMembers = memberService.findAll();
        return new ResponseEntity<>(myMembers,
            HttpStatus.OK);
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
//    @ApiOperation(value = "adds new members to the database from a CSV file")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200,
//            message = "OK",
//            response = Member.class,
//            responseContainer = "list"),
//        @ApiResponse(code = 400,
//            message = "Bad Request",
//            response = ErrorDetail.class)})
//    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
//    public ResponseEntity<?> uploadMembers(
//        @ApiParam(value = "a CSV file of memberid strings",
//            required = true)
//            MultipartFile csvfile) throws Exception {
//        List<Member> csvMembers = memberService.saveNewMembers(csvfile.getInputStream());
//        return new ResponseEntity<>(csvMembers, HttpStatus.OK);

    //}

    @RequestMapping(value = "/member/{javaId}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a member with the path parameter id",
        response = Member.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found",
            response = Member.class),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> getMemberByJavaId(
        @ApiParam(value = "member id",
            required = true,
            example = "7")
        @PathVariable
            Long javaId)
    {
        Member m = memberService.findMemberByJavaId(javaId);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @RequestMapping(value = "/member/id/{memberid}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a member from the path parameter memberid string",
        response = Member.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found",
            response = Member.class),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> getMemberByMemberId(
        @ApiParam(value = "memberid",
            required = true,
            example = "m1234567id")
        @PathVariable String memberid) {
        Member m = memberService.findMemberByStringId(memberid);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @RequestMapping(value = "/member/id/like/{partialmemberId}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a list of members with ids matching partial id string path parameter",
        response = Member.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Members Found",
            response = Member.class,
            responseContainer = "List")
    })
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> getMemberLikeId(
        @ApiParam(value = "partial memberid",
            required = true,
            example = "m123")
        @PathVariable String partialmemberId) {
        List<Member> m = memberService.findByIdContaining(partialmemberId);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @RequestMapping(value = "/memebr/{javaId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "deletes a member with the memberid")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found"),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    public ResponseEntity<?> deleteMemberByJavaId(
        @ApiParam(value = "member id",
            required = true,
            example = "7")
        @PathVariable long javaId) {
        memberService.delete(javaId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/member", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "adds one member to the database from the request body string memberid")
    @ApiResponses(value = {
        @ApiResponse(code = 201,
            message = "Member Created"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    public ResponseEntity<?> addNewMember(
        @ApiParam(value = "a memberid string",
            required = true)
        @RequestBody
            String newmember) {

        Member addedMember = memberService.saveNewMember(newmember);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI addedMemberURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{memberid}")
            .buildAndExpand(addedMember.getId())
            .toUri();
        responseHeaders.setLocation(addedMemberURI);

        return new ResponseEntity<>(addedMember,
            responseHeaders,
            HttpStatus.OK);
    }
}