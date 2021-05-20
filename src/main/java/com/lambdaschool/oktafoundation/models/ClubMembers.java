package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "clubmembers")
@IdClass(ClubMembersId.class)
public class ClubMembers extends  Auditable implements Serializable {
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = {"members", "programs", "users"}, allowSetters = true)
    private Club club;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "memberid")
    @JsonIgnoreProperties(value = {"clubs", "reactions"}, allowSetters = true)
    private Member member;

    public ClubMembers() {
    }

    public ClubMembers(Club club, Member member) {
        this.club = club;
        this.member = member;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ClubMembers))
        {
            return false;
        }
        ClubMembers that = (ClubMembers) o;
        return ((club == null) ? 0 : club.getClubid()) == ((that.club == null) ? 0 : that.club.getClubid()) && ((member == null) ? 0 : member.getMemberid()) == ((that.member == null) ? 0 : that.member.getMemberid());

    }
    @Override
    public int hashCode()
    {
        return 90;
    }
}
