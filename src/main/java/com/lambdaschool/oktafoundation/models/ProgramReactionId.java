package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProgramReactionId implements Serializable {
    private Long program;

    private Long reactions;

    public ProgramReactionId() {
    }

    public Long getProgram() {
        return program;
    }

    public void setProgram(Long program) {
        this.program = program;
    }

    public Long getReactions() {
        return reactions;
    }

    public void setReactions(Long reactions) {
        this.reactions = reactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProgramReactionId that = (ProgramReactionId) o;
        return Objects.equals(program, that.program) && Objects.equals(reactions, that.reactions);
    }

    @Override
    public int hashCode() {
        return 7;
    }
}
