package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MemberService
{
    List<Member> findAll();

    Member save(Member member);

    Member saveNewMember(String member);

    List<Member> saveNewMembers(InputStream stream) throws IOException;

    Member findMemberByJavaId(long id);

    Member findMemberByStringId(String memberId);

    List<Member> findByIdContaining(String partialmemberId);

    void delete(long id);

//    Member saveNewMember(String newmember);

    /**
     * Deletes all record and their associated records from the database
     */
    public void deleteAll();
}