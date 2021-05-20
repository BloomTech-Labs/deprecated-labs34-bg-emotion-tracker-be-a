package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubMembers;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClubMembersRepository extends CrudRepository<ClubMembers, Long> {
    Optional<ClubMembers> findClubMembersByClubIdAndMemberId(Long clubid, String memberid);
}
