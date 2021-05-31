package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ClubPrograms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service( value = "clubProgramService")
public class ClubProgramServiceImpl implements ClubProgramService{
    @Autowired
    private ClubProgramRepository clubprogramrepos;

    @Override
    public ClubPrograms findClubProgramById(Long clubactivityid) {
        return clubprogramrepos.findById(clubactivityid)
                .orElseThrow(() -> new ResourceNotFoundException("Club Acitivity id" + clubactivityid
                + "not found"));
    }

    @Override
    public List<ClubPrograms> findAll(){
        List<ClubPrograms> list = new ArrayList<>();

        clubprogramrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }
}
