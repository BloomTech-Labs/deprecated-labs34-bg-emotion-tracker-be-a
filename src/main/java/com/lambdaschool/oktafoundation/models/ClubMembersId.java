package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClubMembersId implements Serializable {

    private long clubid;

    private long memberid;

    public ClubMembersId() {
    }

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public long getMemberid() {
        return memberid;
    }

    public void setMemberid(long memberid) {
        this.memberid = memberid;
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
        return memberid == that.memberid && clubid == that.clubid;
    }

    @Override
    public int hashCode(){
        return 45;
    }
}
