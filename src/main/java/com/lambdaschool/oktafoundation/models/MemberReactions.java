package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "MemberReaction")
@Table(name = "memberreactions")
public class MemberReactions extends  Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberreactionid;

    @ManyToOne
    @JoinColumn(name = "memberid")
    @JsonIgnoreProperties(value = "reactions", allowSetters = true)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "reactionid")
    @JsonIgnoreProperties(value = "member", allowSetters = true)
    private Reactions reactions;

    @Column(nullable = false)
    private Boolean ischeckedin;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "programid"),
            @JoinColumn(name = "clubid")})
    @JsonIgnoreProperties(value = {"reactions"}, allowSetters = true)
    private ClubPrograms clubProgram;

    public MemberReactions (Member member, Reactions reactions, Boolean ischeckedin, ClubPrograms clubPrograms) {
        this.member = member;
        this.reactions = reactions;
        this.ischeckedin = ischeckedin;
        this.clubProgram = clubPrograms;
    }

    public MemberReactions() {
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Reactions getReactions() {
        return reactions;
    }

    public void setReactions(Reactions reactions) {
        this.reactions = reactions;
    }

    public Boolean getIscheckedin() {
        return ischeckedin;
    }

    public void setIscheckedin(Boolean ischeckedin) {
        this.ischeckedin = ischeckedin;
    }


    public long getMemberreactionid() {
        return memberreactionid;
    }

    public void setMemberreactionid(long memberreactionid) {
        this.memberreactionid = memberreactionid;
    }

    public ClubPrograms getClubProgram() {
        return clubProgram;
    }

    public void setClubProgram(ClubPrograms clubProgram) {
        this.clubProgram = clubProgram;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MemberReactions that = (MemberReactions) o;

        return ((member == null) ? 0 : member.getMemberid()) == ((that.member == null) ? 0 : that.member.getMemberid()) && ((reactions == null) ? 0 : reactions.getReactionid()) == ((that.reactions == null) ? 0 : that.reactions.getReactionid()) && clubProgram.equals(that.getClubProgram());


    }
    @Override
    public int hashCode(){
        return 44;
    }
}
