package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value="Member",
    description = "A member record with primary key id and a memberid string")
@Entity
@Table(name = "members")
public class Member extends Auditable
{
    @ApiModelProperty(name = "id",
        value = "primary key for member",
        required = true,
        example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ApiModelProperty(name = "memberid",
        value = "bg club memberid string",
        required = true,
        example = "m1234567id")
    @NotNull
    @Column(unique = true)
    private String memberid;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"member", "memberReactions", "reactions"}, allowSetters = true)
    private Set <MemberReactions> reactions = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "member", allowSetters = true)
    private Set<ClubMembers> clubMember = new HashSet<>();

    public Member()
    {
    }

    public Member(String memberid) {
        this.memberid = memberid;
    }

    public Member(long id, @NotNull String memberid) {
        this.id = id;
        this.memberid = memberid;
    }

    public Set<MemberReactions> getReactions() {
        return reactions;
    }

    public void setReactions(Set<MemberReactions> reactions) {
        this.reactions = reactions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Set<ClubMembers> getClubs() {
        return clubMember;
    }

    public void setClubs(Set<ClubMembers> clubs) {
        this.clubMember = clubs;
    }
}