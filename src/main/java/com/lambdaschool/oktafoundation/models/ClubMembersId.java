package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClubMembersId implements Serializable {

    private long club;

    private long member;

    public ClubMembersId() {
    }

    public long getClubid() {
        return club;
    }

    public void setClubid(long clubid) {
        this.club = clubid;
    }

    public long getMemberid() {
        return member;
    }

    public void setMemberid(long memberid) {
        this.member = memberid;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ClubMembersId that = (ClubMembersId) o;
        return member == that.member && club == that.club;
    }

    @Override
    public int hashCode(){
        return 45;
    }
}
