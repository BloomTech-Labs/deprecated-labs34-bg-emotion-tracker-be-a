package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Program;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ProgramService {
    List<Program> findAll();

    Program findProgramById(long id);

    Program findByName(String name);

    void deleteAll();

    void delete(long id);

    Program save(Program program);

    Program update(long id, Program newProgram);

    List<Program> saveNewPrograms(InputStream stream) throws IOException;
}

