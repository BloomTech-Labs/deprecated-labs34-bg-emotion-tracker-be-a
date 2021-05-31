package com.lambdaschool.oktafoundation.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClubMembersRepository extends CrudRepository<ClubMembers, Long> {
    Optional<ClubMembers> findClubMembersByClub_ClubidAndMemberId_Memberid(Long clubid, String memberid);
}
