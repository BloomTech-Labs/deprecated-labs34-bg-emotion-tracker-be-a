package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubPrograms;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClubProgramRepository extends CrudRepository <ClubPrograms, Long>
{
    Optional <ClubPrograms> getClubProgramsByProgram_ProgramidAndClub_Clubid(Long programid, Long clubid);
}
