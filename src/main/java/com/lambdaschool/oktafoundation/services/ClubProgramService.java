package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubPrograms;

import java.util.List;

public interface ClubProgramService {

    ClubPrograms findClubProgramById(Long clubactivityid);

//    abstract ClubPrograms findClubProgramById(Long clubprogramid);

    List<ClubPrograms> findAll();
}
