package com.lambdaschool.oktafoundation.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Member()
    {
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
}