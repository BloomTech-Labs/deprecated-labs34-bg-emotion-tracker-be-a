package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.Program;
import org.springframework.data.repository.CrudRepository;

public interface ClubRepository extends CrudRepository<Club, Long>
{
    Club findByClubnameIgnoreCase(String name);
}

