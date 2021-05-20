package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "memberReaectionService")
public class MemberReactionServiceImpl implements MemberReactionService{

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    @Override
    public List <MemberReactions> findAll(){
        List<MemberReactions> memberReactionsList = new ArrayList<>();

        memberReactionRepository.findAll().iterator().forEachRemaining(memberReactionsList::add);
        return memberReactionsList;
    }

    @Override
    public MemberReactions findMemberReactionById(Long id) throws ResourceNotFoundException
    {
        return memberReactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member Reaction id" + id + "not found."));
    }

    @Override
    public MemberReactions save(MemberReactions newReaction) {
        MemberReactions memberReaction = new MemberReactions();

        if (newReaction.getMemberreactionid() != 0) {
            memberReactionRepository.findById(memberReaction.getMemberreactionid())
                    .orElseThrow(() -> new EntityNotFoundException("Member Reaction id" + memberReaction.getMemberreactionid() + " not found!"));
        }
        memberReaction.setMember(newReaction.getMember());
        memberReaction.setClubProgram(newReaction.getClubProgram());
        memberReaction.setReactions(newReaction.getReactions());
        memberReaction.setIscheckedin(Boolean.TRUE);


        return memberReactionRepository.save(memberReaction);


    }
}
