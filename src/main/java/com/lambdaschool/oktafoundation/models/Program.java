package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table (name = "programs")
public class Program extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long programid;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties (value = "program", allowSetters = true)
    private Set<ClubPrograms> club = new HashSet<>();

    public Program() {
    }

    public Program(String name) {
        this.name = name.toLowerCase();
    }

    public long getProgramid() {
        return programid;
    }

    public void setProgramid(long programid) {
        this.programid = programid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public Set<ClubPrograms> getClubs() {
        return club;
    }

    public void setClubs(Set<ClubPrograms> clubs) {
        this.club = clubs;
    }
}
