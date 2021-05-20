package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Reactions;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReactionRepository extends CrudRepository<Reactions, Long>
{
    Optional<Reactions> findReactionsByReactionString(String value);
}
