package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubPrograms;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.repository.ClubProgramRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.services.ProgramService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubProgramRepository clubProgramRepository;

    @Autowired
    private MemberReactionRepository memberReactionRepository;


    /**
     * List of all Programs
     * @return JSON List of all the programs
     * @see ProgramService#findAll()
     */
    @GetMapping(value ="/programs",
        produces = "application/json")
    public ResponseEntity<?> listPrograms(){
        List<Program> allPrograms = programService.findAll();
        return new ResponseEntity<>(allPrograms, HttpStatus.OK);
    }

    /**
     * The Program referenced by the given primary key
     * @param programId The primary key (long) of the program you seek
     * @return JSON object of the program you seek
     * @see ProgramService#findProgramById(long) ProgramService.findProgramById(long programId)
     */
    @GetMapping(value = "/program/{programid}",
        produces = "application/json")
    public ResponseEntity<?> getProgramById(@PathVariable Long programId){
        Program p = programService.findProgramById(programId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    /**
     * The Program with the given name
     * @param programName The name of the program you seek
     * @return JSON object of the program you seek
     * @see ProgramService#findByName(String) ProgramService.findByName(String programName)
     */
    @GetMapping(value = "/program/name/{programname}",
        produces = "applcation/json")
    public ResponseEntity<?> getProgramByName(@PathVariable String programName){
        Program p = programService.findByName(programName);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<?> uploadPrograms(
        MultipartFile csvfile) throws Exception {
        List<Program> addedPrograms = programService.saveNewPrograms(csvfile.getInputStream());
        if (addedPrograms.size() < 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(addedPrograms, HttpStatus.CREATED);
        }
    }

    /**
     *
     * @param newProgram A complete new Program
     * @return A location header with the URI to the newly created Program and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see ProgramService.save(Program)
     */

    @PostMapping(value = "/program",
        consumes = "application/json")
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
    @PutMapping(value = "/program/{programid}",
        consumes = {"application/json"})
    public ResponseEntity<?> updateProgram(
        @PathVariable
            long programid,
        @Valid
        @RequestBody
            Program newProgram){
        newProgram = programService.update(programid, newProgram);
        return new ResponseEntity<>(HttpStatus.OK);
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
        club.getPrograms().add(temp);
        clubRepository.save(club);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given a flexible activity object and a clubId, remove the activity from the Club with the given id.
     *
     * @param program A program that exists in a club
     * @param clubid The id of the club to which the program should be removed
     */
    @PostMapping(value = "/program/removefromclub/{clubid}", consumes = "application/json")
    public ResponseEntity<?> removeActivityFromClub(@RequestBody Program program, @PathVariable long clubid)
    {
        ClubPrograms cp;

        if (program.getProgramid() == 0){
            // no id provided, try to find by name
            try {
                var temp = programService.findByName(program.getName());
                cp = clubProgramRepository.getClubProgramsByProgramIdByClubId(temp.getProgramid(), clubid).orElseThrow();
                memberReactionRepository.getMemberReactionsByClubProgram_ClubandClibProgram_Program(cp.getClub(), cp.getProgram())
                        .forEach(i -> memberReactionRepository.delete(i));
                clubProgramRepository.delete(cp);
            } catch (Exception e) {
                return new ResponseEntity<>("No such club activity", HttpStatus.NOT_MODIFIED);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
