package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClubProgramsId
    implements Serializable {

    private Long club;

    private Long program;

    public ClubProgramsId() {
    }

    public long getClub() {
        return club;
    }

    public void setClub(long club) {
        this.club = club;
    }

    public long getProgram() {
        return program;
    }

    public void setProgram(long program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClubProgramsId that = (ClubProgramsId) o;
        return getClub() == that.getClub() && getProgram() == that.getProgram();
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
