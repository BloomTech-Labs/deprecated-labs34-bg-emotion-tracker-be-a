package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.Program;
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
import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    @Autowired
    ProgramService programService;

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
}
