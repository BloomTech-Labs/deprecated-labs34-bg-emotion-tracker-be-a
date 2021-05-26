package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table (name = "programs")
public class Program extends Auditable{
    @ApiModelProperty(name = "id",
        value = "primary key for program",
        example = "2")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long programid;

    @ApiModelProperty(name = "programname",
        value = "bg club program name String",
        example = "basketball")
    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties (value = "program", allowSetters = true)
    private Set<ClubPrograms> clubs = new HashSet<>();

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
        return clubs;
    }

    public void setClubs(Set<ClubPrograms> clubs) {
        this.clubs = clubs;
    }
}
