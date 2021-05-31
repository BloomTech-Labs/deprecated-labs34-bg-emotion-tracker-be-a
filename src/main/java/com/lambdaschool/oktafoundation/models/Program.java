package com.lambdaschool.oktafoundation.models;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "program_reactions",
        joinColumns = {@JoinColumn(name = "programid")},
        inverseJoinColumns = {@JoinColumn(name = "reactionid")})
    private Set<Reactions> programReactions = new HashSet<Reactions>(0);

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

    public Set<Reactions> getProgramReactions() {
        return programReactions;
    }

    public void setProgramReactions(Set<Reactions> programReactions) {
        this.programReactions = programReactions;
    }
}
