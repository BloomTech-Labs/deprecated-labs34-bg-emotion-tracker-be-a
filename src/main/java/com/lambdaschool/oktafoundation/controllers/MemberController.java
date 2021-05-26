package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ErrorDetail;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import com.lambdaschool.oktafoundation.services.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @ApiOperation(value = "returns all Members",
        response = Member.class,
        responseContainer = "List")
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    @GetMapping(value = "/members",
        produces = "application/json")
    public ResponseEntity<?> listAllMembers() {
        List<Member> myMembers = memberService.findAll();
        return new ResponseEntity<>(myMembers,
            HttpStatus.OK);
    }

    @ApiOperation(value = "adds new members to the database from a CSV file")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "OK",
            response = Member.class,
            responseContainer = "list"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> uploadMembers(
        @ApiParam(value = "a CSV file of memberid strings",
            required = true)
            MultipartFile csvfile) throws Exception {
        List<Member> csvMembers = memberService.saveNewMembers(csvfile.getInputStream());
        return new ResponseEntity<>(csvMembers, HttpStatus.OK);

    }

    @ApiOperation(value = "returns a member with the path parameter id",
        response = Member.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found",
            response = Member.class),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    @GetMapping(value = "/member/{javaId}",
        produces = "application/json")
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

    @ApiOperation(value = "returns a member from the path parameter memberid string",
        response = Member.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found",
            response = Member.class),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    @GetMapping(value = "/member/id/{memberId}",
        produces = "application/json")
    public ResponseEntity<?> getMemberByMemberId(
        @ApiParam(value = "memberid",
            required = true,
            example = "m1234567id")
        @PathVariable String memberId) {
        Member m = memberService.findMemberByStringId(memberId);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @ApiOperation(value = "returns a list of members with ids matching partial id string path parameter",
        response = Member.class,
        responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Members Found",
            response = Member.class,
            responseContainer = "List")
    })
    @PreAuthorize("hasAnyRole('SUPERADMIN, CLUBDIR')")
    @GetMapping(value = "/member/id/like/{partialmemberId}",
        produces = "application/json")
    public ResponseEntity<?> getMemberLikeId(
        @ApiParam(value = "partial memberid",
            required = true,
            example = "m123")
        @PathVariable String partialmemberId) {
        List<Member> m = memberService.findByIdContaining(partialmemberId);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @ApiOperation(value = "deletes a member with the memberid")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Member Found"),
        @ApiResponse(code = 404,
            message = "Member Not Found",
            response = ResourceNotFoundException.class)})
    @DeleteMapping(value = "/member/{javaId}")
    public ResponseEntity<?> deleteMemberByJavaId(
        @ApiParam(value = "member id",
            required = true,
            example = "7")
        @PathVariable long javaId) {
        memberService.delete(javaId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @ApiOperation(value = "adds one member to the database from the request body string memberid")
    @ApiResponses(value = {
        @ApiResponse(code = 201,
            message = "Member Created"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    @PostMapping(value = "/member",
        consumes = "application/json")
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

    /**
     * Given a memberID, remove the member with that ID
     *
     * @param mid The memberID of the member you want to delete.
     * @throws URISyntaxException If the member doesn't exist
     */
//    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
//    @DeleteMapping(value = "/removeMember/{mid}")
//    public ResponseEntity<?> removeMember(@PathVariable String mid) throws URISyntaxException {
//        var member = memberRepository.findById(mid).orElseThrow(() -> new ResourceNotFoundException("Member" + mid + "not found"));
//
//        if (member.isPresent()) {
//
//        }
//    }

    /**
     * Given a list of member object with memberids, add the members to the database.
     *
     * @param members The list of members object you want to add
     * @throws URISyntaxException If the member doesn't exist
     */
//    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
//    @PostMapping(value = "/addMembers")
//    public ResponseEntity<?> addMembers(@RequestBody List<Member> members) throws URISyntaxException {
//        for (var i: members) {
//
//
//        }
//    }
}