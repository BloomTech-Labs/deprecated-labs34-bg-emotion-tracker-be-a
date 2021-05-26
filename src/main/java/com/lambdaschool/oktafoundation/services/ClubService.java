package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Club;

import java.util.List;

public interface ClubService
{
    List<Club> findAll();

    Club save(Club club);

    void deleteAll();

    Club findClubById(Long clubid);

    Club update(Club club, long clubid);
}
