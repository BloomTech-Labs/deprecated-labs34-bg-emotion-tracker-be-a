package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel (value = "MemberReaction")
@Entity(name = "MemberReaction")
@Table(name = "memberreactions")
public class MemberReactions extends  Auditable implements Serializable {
    @ApiModelProperty(name = "memberreactionid",
        value = "primary key for memberreaction",
        required = true,
        example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberreactionid;

    @ManyToOne
    @JoinColumn(name = "member_table_id")
    @JsonIgnoreProperties(value = "member", allowSetters = true)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "reactionid")
    @JsonIgnoreProperties(value = "reactions", allowSetters = true)
    private Reactions reactions;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "programid"),
                  @JoinColumn(name = "clubid")})
    @JsonIgnoreProperties(value = {"reactions"}, allowSetters = true)
    private ClubPrograms clubprograms;

    public MemberReactions() {
    }

    public MemberReactions(Member member, Reactions reactions, ClubPrograms clubprograms) {
        this.member = member;
        this.reactions = reactions;
        this.clubprograms = clubprograms;
    }

    public long getMemberreactionid() {
        return memberreactionid;
    }

    public void setMemberreactionid(long memberreactionid) {
        this.memberreactionid = memberreactionid;
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

    public ClubPrograms getClubprograms() {
        return clubprograms;
    }

    public void setClubprograms(ClubPrograms clubprograms) {
        this.clubprograms = clubprograms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberReactions that = (MemberReactions) o;
        return ((member == null) ? 0 : member.getMemberid()) == ((that.member == null) ? 0 : that.member.getMemberid()) &&
            ((reactions == null) ? 0 : reactions.getReactionid()) == ((that.reactions == null) ? 0 : that.reactions.getReactionid()) &&
            clubprograms.equals(that.getClubprograms());
    }

    @Override
    public int hashCode() {
        return 7;
    }
}
