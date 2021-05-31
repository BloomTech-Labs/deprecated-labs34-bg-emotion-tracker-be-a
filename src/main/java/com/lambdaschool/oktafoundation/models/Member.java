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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "member_reactions",
        joinColumns =  {@JoinColumn(name = "memberid")},
        inverseJoinColumns = {@JoinColumn(name = "reactionid")})
    private Set<Reactions> memberReactons = new HashSet<Reactions>(0);


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

    public Set<Reactions> getMemberReactons() {
        return memberReactons;
    }

    public void setMemberReactons(Set<Reactions> memberReactons) {
        this.memberReactons = memberReactons;
    }
}