package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubPrograms;
import com.lambdaschool.oktafoundation.repository.ClubProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service( value = "clubProgramService")
public class ClubProgramServiceImpl implements ClubProgramService{
    @Autowired
    private ClubProgramRepository clubProgramRepository;

    @Override
    public ClubPrograms findClubProgramById(Long clubprogramid) {
        return clubProgramRepository.findById(clubprogramid)
                .orElseThrow(()-> new ResourceNotFoundException("Club Program id" + clubprogramid + "not found."));
    }

    @Override
    public List<ClubPrograms> findAll(){
        List<ClubPrograms> clubProgramsList = new ArrayList<>();

        clubProgramRepository.findAll().iterator().forEachRemaining(clubProgramsList::add);
        return clubProgramsList;
    }
}
