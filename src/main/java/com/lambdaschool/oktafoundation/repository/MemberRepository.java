package com.lambdaschool.oktafoundation.repository;


import com.lambdaschool.oktafoundation.models.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long>
{
    Member findMemberByMemberid(String memberid);

    List<Member> findMembersByMemberidContaining(String partialmemberId);
}
