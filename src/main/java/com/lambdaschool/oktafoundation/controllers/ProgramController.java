package com.lambdaschool.oktafoundation.controllers;


import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubPrograms;
import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ErrorDetail;
import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ProgramService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Api(value = "/programs")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @Autowired
    private ClubRepository clubRepository;




    /**
     * List of all Programs
     * @return JSON List of all the programs
     * @see ProgramService#findAll()
     */
    @RequestMapping(value = "/programs", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns all Programs",
        response = Program.class,
        responseContainer = "List")
    @PreAuthorize ("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> listPrograms(){
        List<Program> allPrograms = programService.findAll();
        return new ResponseEntity<>(allPrograms, HttpStatus.OK);
    }

    /**
     * The Program referenced by the given primary key
     * @param programid The primary key (long) of the program you seek
     * @return JSON object of the program you seek
     * @see ProgramService#findProgramById(long) ProgramService.findProgramById(long programId)
     */
    @RequestMapping(value = "/program/{programid}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a program with the path parameter id",
        response = Program.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
        message = "Program Found",
        response = Program.class),
        @ApiResponse(code = 404,
        message = "Program Not Found",
        response = ResourceNotFoundException.class)})
    public ResponseEntity<?> getProgramById(@PathVariable Long programid){
        Program p = programService.findProgramById(programid);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    /**
     * The Program with the given name
     * @param programname The name of the program you seek
     * @return JSON object of the program you seek
     * @see ProgramService#findByName(String) ProgramService.findByName(String programName)
     */
    @RequestMapping(value = "/program/name/{programname}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a program with the path parameter programname",
        response = Program.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Program Found",
            response = Program.class),
        @ApiResponse(code = 404,
            message = " Program Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> getProgramByName(@PathVariable String programname){
        Program p = programService.findByName(programname);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation(value = "adds new programs to the databse from a CSV file")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "OK",
            response = Program.class,
            responseContainer = "list"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> uploadPrograms(
        MultipartFile csvfile) throws Exception {
        List<Program> addedPrograms = programService.saveNewPrograms(csvfile.getInputStream());
        if (addedPrograms.size() < 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(addedPrograms, HttpStatus.CREATED);
        }
    }


    @RequestMapping(value = "/program", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "adds one program to the database from the request body Program Object programname")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Program Created"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> addNewProgram(@Valid @RequestBody Program newProgram) throws URISyntaxException{
        newProgram.setProgramid(0);
        newProgram = programService.save(newProgram);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProgramURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("?{userid}")
            .buildAndExpand(newProgram.getProgramid())
            .toUri();
        responseHeaders.setLocation(newProgramURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /**
     * Allows you to update a program name only
     * @param programid The primary key of the program you wish to change
     * @param newProgram The new name(String) for the program
     * @return Status OK
     */
    @RequestMapping(value ="/program/{programid}", method = RequestMethod.PUT, consumes = "application/json")
    @ApiOperation(value = "updates the given programid with a new program")
    @ApiResponses(value = {
        @ApiResponse(code = 201,
            message = "Program Created"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('ADMIN, CD')")
    public ResponseEntity<?> updateProgram(
        @PathVariable
            long programid,
        @Valid
        @RequestBody
            Program newProgram){
        newProgram = programService.update(programid, newProgram);
        return new ResponseEntity<>(newProgram, HttpStatus.OK);
    }

    /**
     * Given a flexible program object and a clubId, adds the program to the Club with the given id
     * If the activity is given by name and does not exist, it would be created
     * @param program, A Program to add to an existing Club
     * @param clubid, The id of the club where the Program should be added
     */
    @PostMapping(value = "/program/addtoclub/{clubid}", consumes = "application/json")
    public ResponseEntity<?> addProgramToClub(@RequestBody Program program, @PathVariable long clubid){
        Program newprogram;

        if (program.getProgramid() == 0){
            try {
                newprogram = programService.findByName(program.getName());
            } catch (Exception e) {
                newprogram = programService.findProgramById(program.getProgramid());
            }
        } else {
            newprogram = programService.findProgramById(program.getProgramid());
        }
        Club club = clubRepository.findById(clubid).orElseThrow();
        ClubPrograms temp = new ClubPrograms(club, newprogram);
        club.getProgram().add(temp);
        clubRepository.save(club);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
