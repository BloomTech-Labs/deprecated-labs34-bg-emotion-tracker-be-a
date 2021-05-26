package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "ClubPrograms")
@Entity
@Table(name = "clubprograms")
@IdClass(ClubProgramsId.class)
public class ClubPrograms
    extends Auditable
    implements Serializable {

    @ApiModelProperty(name = "clubid",
        value = "primary key for club",
        required = true,
        example = "1")
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties (value = "programs",
        allowSetters = true)
    private Club club;

    @ApiModelProperty(name = "programid",
        value = "the primary key for program",
        required = true,
     example = "2")
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "programid")
    @JsonIgnoreProperties(value = "clubs", allowSetters = true)
    private Program program;

    // Join table of club programs and member reactions
    @Column
    @OneToMany(
            mappedBy = "clubProgram",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberReactions> reactions = new HashSet<>();

    public ClubPrograms(){

    }

    public ClubPrograms(Club club, Program program){
        this.club = club;
        this.program = program;

    }

    public ClubPrograms(Club club, Program program, Set <MemberReactions> reactions){
        this.club = club;
        this.program = program;
        this.reactions = reactions;
    }

    public Set<MemberReactions> getMemberReactions() {
        return reactions;
    }

    public void setMemberReactions(Set<MemberReactions> reactions) {
        this.reactions = reactions;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClubPrograms)) {
            return false;
        }
        ClubPrograms that = (ClubPrograms) o;
        return ((club == null) ? 0 : club.getClubid()) == ((that.club == null) ? 0 : that.club.getClubid()) &&
            ((program == null) ? 0 : program.getProgramid()) == ((that.program == null) ? 0 : that.program.getProgramid());
    }

    @Override
    public int hashCode() {
        return 7;
    }
}
