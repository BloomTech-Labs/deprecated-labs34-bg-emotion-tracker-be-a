package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Program;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProgramRepository
    extends CrudRepository<Program, Long> {
    Program findByNameIgnoreCase(String name);

    @Transactional
    @Modifying
    @Query (value = "UPDATE programs SET name = :name, last_modified_by = :uname, last_modified_date = CURRENT_TIMESTAMP WHERE programid = :programid",
        nativeQuery = true)
    void updateProgram(
        String uname,
        long programid,
        String name);
}
