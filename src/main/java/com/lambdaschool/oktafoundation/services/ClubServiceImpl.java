package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubPrograms;
import com.lambdaschool.oktafoundation.models.Program;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "clubService")
public class ClubServiceImpl implements ClubService
{
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ProgramService programService;

    @Override
    public List<Club> findAll()
    {
        List<Club> list = new ArrayList<>();

        clubRepository.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Club save(Club club)
    {
        Club newClub = new Club();
        if (club.getClubid() != 0)
        {
            clubRepository.findById(club.getClubid())
                .orElseThrow(() -> new ResourceNotFoundException("Club id " + club.getClubid() + " not found!"));
            newClub.setClubid(club.getClubid());
        }

        newClub.setClubname(club.getClubname());
        newClub.setClubdirector(club.getClubdirector());

        newClub.getPrograms().clear();
        for (ClubPrograms cp : club.getPrograms())
        {
            Program addProgram = programService.findProgramById(cp.getProgram().getProgramid());
            newClub.getPrograms().add(new ClubPrograms(newClub, addProgram));
        }

        return clubRepository.save(newClub);
    }

    @Override
    public void deleteAll()
    {
        clubRepository.deleteAll();
    }

    @Override
    public Club findClubById(Long clubid) throws ResourceNotFoundException{
        return clubRepository.findById(clubid)
                .orElseThrow(() -> new ResourceNotFoundException("Club id" + clubid + "not found!"));
    }

}