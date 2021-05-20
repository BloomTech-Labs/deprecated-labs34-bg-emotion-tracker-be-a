package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubMembers;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ClubMembersRepository extends CrudRepository<ClubMembers, Long> {
//    Optional<ClubMembers> findClubMembersByClubIdAndMemberId(Long clubid, String memberid);

}
