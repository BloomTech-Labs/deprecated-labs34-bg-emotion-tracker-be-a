package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubPrograms;

import java.util.List;

public interface ClubProgramService {

    static ClubPrograms findClubProgramById(Long clubprogramid);

    List<ClubPrograms> findAll();
}
