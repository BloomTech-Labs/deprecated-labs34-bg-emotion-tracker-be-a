package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.models.Program;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberReactionRepository extends CrudRepository<MemberReactions, Long> {
    List<MemberReactions> getMemberReactionsByClubProgram_ClubandClibProgram_Program(Club c, Program p);

    List<MemberReactions> getMemberReactionsByMember(Member m);
}
