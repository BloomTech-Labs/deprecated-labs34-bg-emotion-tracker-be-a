package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Reactions;

import java.util.List;

public interface ReactionsService {
    List<Reactions> findAll();

    Reactions findReactionById(long id);

    Reactions save(Reactions reaction);

    Reactions findByName(String name);

    Reactions update(long id, Reactions reaction);

    void deleteAll();
}
