package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "programreactions")
@IdClass (ProgramReactionId.class)
public class ProgramReactions extends Auditable implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "programid")
    @JsonIgnoreProperties(value = "reactions", allowSetters = true)
    private Program program;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "reactionid")
    @JsonIgnoreProperties(value = "programs", allowSetters = true)
    private Reactions reactions;

    public ProgramReactions() {
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Reactions getReactions() {
        return reactions;
    }

    public void setReactions(Reactions reactions) {
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
        ProgramReactions that = (ProgramReactions) o;
        return ((program == null) ? 0 : program.getProgramid()) == ((that.program == null) ? 0 : that.program.getProgramid()) && ((reactions == null) ? 0 : reactions.getReactionid()) == ((that.reactions == null) ? 0 : that.reactions.getReactionid());
    }
    @Override
    public int hashCode(){
        return 7;
    }
}
