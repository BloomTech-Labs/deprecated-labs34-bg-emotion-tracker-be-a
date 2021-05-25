package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "memberReaectionService")
public class MemberReactionServiceImpl implements MemberReactionService{

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    @Autowired
    private MemberRepository memberrepos;

    @Override
    public List <MemberReactions> findAll(){
        List<MemberReactions> memberReactionsList = new ArrayList<>();
        memberReactionRepository.findAll()
            .iterator()
            .forEachRemaining(memberReactionsList::add);
        return memberReactionsList;
    }

    @Override
    public MemberReactions findMemberReactionById(Long id) throws ResourceNotFoundException
    {
        return memberReactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member Reaction id" + id + "not found."));
    }
}
