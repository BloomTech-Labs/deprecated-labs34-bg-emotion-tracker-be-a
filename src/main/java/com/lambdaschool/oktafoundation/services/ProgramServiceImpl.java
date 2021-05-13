package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceFoundException;
import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubPrograms;

import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.models.UserRoles;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service (value = "programService")
public class ProgramServiceImpl
    implements ProgramService {
    @Autowired
    ProgramRepository programrepos;

    @Autowired
    ClubRepository clubrepos;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Program> findAll() {
        List<Program> list = new ArrayList<>();
        programrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Program findProgramById(long id) {
        return programrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Program id " + id + " not found!"));
    }

    @Override
    public Program findByName(String name) throws ResourceNotFoundException{
        Program pr = programrepos.findByNameIgnoreCase(name);
        if(pr == null){
            throw new ResourceNotFoundException("Program " + name + " not found!");
        }
        return pr;
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        programrepos.deleteAll();
    }

    @Transactional
    @Override
    public void delete(long id) {
        if(programrepos.findById(id)
            .isPresent()){
            programrepos.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Program id " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public Program save(Program program) {
        if (program.getClubs()
            .size() > 0)
        {
            throw new ResourceFoundException("Club Programs are not updated through Program.");
        }

        return programrepos.save(program);
    }

    @Override
    public Program update(long id, Program program) {
        if(program.getName() == null){
            throw new ResourceNotFoundException("No program name found to update!");
        }
        if(program.getClubs().size() > 0){
            throw new ResourceFoundException("Club Programs are not updated through Programs.");
        }

        Program newProgram = findProgramById(id);
        programrepos.updateProgram(userAuditing.getCurrentAuditor().get(),
            id, program.getName());
        return findProgramById(id);
    }

    @Override
    public List<Program> saveNewPrograms(InputStream stream) throws IOException
    {
        List<Program> addedPrograms = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String programname;
        String clubname;
        String row;
        // removes header line from CSV file
        String headerLine = reader.readLine();
        String[] columnheaders = headerLine.split(",");
        List<String> columnheaderlist = Arrays.asList(columnheaders);
        ArrayList<String> listofheaders = new ArrayList<>(columnheaderlist);

        // Validate two columns in headers
        if(listofheaders.size() != 2){
            throw new ResourceNotFoundException("CSV file must contain Program Name and Club headers");
        }

        // Validate correct headers
        if (!(listofheaders.get(0).equalsIgnoreCase("Program Name") && listofheaders.get(1).equalsIgnoreCase("Club")))
        {
            throw new ResourceNotFoundException("CSV file must contain Program Name and Club headers, file contains " + listofheaders.get(0) + " and " + listofheaders.get(1));
        }

        while((row = reader.readLine())!= null)
        {
            String[] rowarray = row.split(",");
            List<String> rowarraylist = Arrays.asList(rowarray);
            List<String> fields = new ArrayList<>(rowarraylist);

            programname = fields.get(0).toLowerCase();
            programname = programname.replaceAll("^\"|\"$",
                "");
            clubname = fields.get(1).toLowerCase();
            clubname = clubname.replaceAll("^\"|\"$",
                "");

            // if club, doesn't exist throw resource not found
            // clubs are not updated through programs
            Club cl = clubrepos.findByClubnameIgnoreCase(clubname);
            if (cl == null)
            {
                throw new ResourceNotFoundException("Club " + clubname + " not found");
            }

            // if program doesn't exist, create it
            Program pr = programrepos.findByNameIgnoreCase(programname);
            if (pr == null)
            {
                pr = programrepos.save(new Program(programname));

            }

            // verify program is not already associated with this club
            // loop through all the programs clubs
            // if any clubprogram has the clubname being added
            // set the boolean flag to false
            // if after the loop, boolean is true
            // add the new relationship
            boolean isNewClubProgram = true;
            for (ClubPrograms cp : pr.getClubs())
            {
                if(cp.getClub().getClubname().equalsIgnoreCase(cl.getClubname())){
                    isNewClubProgram = false;
                }
            }

            if (isNewClubProgram)
            {
                pr.getClubs()
                    .add(new ClubPrograms(cl,
                        pr));
                addedPrograms.add(pr);
            }

        }
        return addedPrograms;
    }
}

