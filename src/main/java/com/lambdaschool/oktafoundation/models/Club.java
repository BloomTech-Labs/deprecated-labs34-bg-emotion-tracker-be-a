package com.lambdaschool.oktafoundation.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@ApiModel(value = "Club",
    description = "A club record with primary key id, clubname String and clubdirector String")
@Entity
@Table(name = "clubs")
public class Club extends Auditable {
    @ApiModelProperty(name = "clubid",
            value = "primary key for club",
            required = true,
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clubid;

    @ApiModelProperty(name = "clubname",
            value = "bg club name String",
            required = true,
            example = "Beach Street")
    @NotNull
    @Column(unique = true)
    private String clubname;

    @ApiModelProperty(name = "clubdirector",
            value = "bg club director String",
            example = "John Smith")
    private String clubdirector;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "club",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "club",
            allowSetters = true)
    private Set<ClubPrograms> program = new HashSet<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "club", allowSetters = true)
    private Set<ClubMembers> members = new HashSet<>();

    public Club() {
    }

    public Club(@NotNull String clubname, String clubdirector) {
        this.clubname = clubname;
        this.clubdirector = clubdirector;
    }

    public Club(
            @NotNull String clubname,
            String clubdirector,
            Set<ClubPrograms> programs) {
        this.clubname = clubname;
        this.clubdirector = clubdirector;
        this.program = program;
    }


    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getClubdirector() {
        return clubdirector;
    }

    public void setClubdirector(String clubdirector) {
        this.clubdirector = clubdirector;
    }

    public Set<ClubPrograms> getProgram() {
        return program;
    }

    public void setProgram(Set<ClubPrograms> program) {
        this.program = program;
    }

    public Set<ClubMembers> getMembers() {
        return members;
    }

    public void setMembers(Set<ClubMembers> members) {
        this.members = members;
    }
}



