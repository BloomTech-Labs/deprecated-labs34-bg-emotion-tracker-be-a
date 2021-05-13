package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "clubprograms")
@IdClass(ClubProgramsId.class)
public class ClubPrograms
    extends Auditable
    implements Serializable {

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties (value = "programs",
        allowSetters = true)
    private Club club;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "programid")
    @JsonIgnoreProperties(value = "clubs")
    private Program program;

    public ClubPrograms(){

    }

    public ClubPrograms(Club club, Program program){
        this.club = club;
        this.program = program;
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
