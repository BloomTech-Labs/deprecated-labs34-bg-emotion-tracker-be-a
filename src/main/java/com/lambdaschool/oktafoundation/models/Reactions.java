package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program")
    private Program program;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}

