package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@ApiModel(value = "ClubMembers",
    description = "A join table for a club member record with primary key club and primary key member")
@Entity
@Table(name = "clubmembers")
@IdClass(ClubMembersId.class)
public class ClubMembers extends  Auditable implements Serializable {
    @ApiModelProperty(name = "clubid",
        value = "the primary key for the club",
        required = true,
        example = "1")
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = {"members", "programs", "users"}, allowSetters = true)
    private Club club;

    @ApiModelProperty(name = "memberid",
        value = "the primary key for the member",
        required = true,
        example = "2")
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "id")
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
