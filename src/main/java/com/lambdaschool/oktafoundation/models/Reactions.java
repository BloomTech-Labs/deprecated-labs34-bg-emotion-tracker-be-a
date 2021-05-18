package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table (name = "reactions")
public class Reactions extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reactionid;

    @NotNull
    @Column(unique = true)
    private String emojiname;

    @NotNull
    @Column(unique = true)
    private String emojicode;

//    @ManyToMany(mappedBy = "reactions")
//    private Set<Member> members;
//
//    @ManyToMany(mappedBy = "reactions")
//    private Set<Program> programs;

    public Reactions() {
    }

    public Reactions(String emojiname, String emojicode) {
        this.emojiname = emojiname;
        this.emojicode = emojicode;
    }

    public long getReactionid() {
        return reactionid;
    }

    public void setReactionid(long reactionid) {
        this.reactionid = reactionid;
    }

    public String getEmojiname() {
        return emojiname;
    }

    public void setEmojiname(String emojiname) {
        this.emojiname = emojiname;
    }

    public String getEmojicode() {
        return emojicode;
    }

    public void setEmojicode(String emojicode) {
        this.emojicode = emojicode;
    }

//    public Set<Member> getMembers() {
//        return members;
//    }
//
//    public void setMembers(Set<Member> members) {
//        this.members = members;
//    }
//
//    public Set<Program> getPrograms() {
//        return programs;
//    }
//
//    public void setPrograms(Set<Program> programs) {
//        this.programs = programs;
//    }
}

