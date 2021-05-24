package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Reactions;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import com.lambdaschool.oktafoundation.repository.ProgramRepository;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service (value = "reactionsService")
public class ReactionsServiceImpl implements ReactionsService {
    @Autowired
    ReactionRepository reactionrepos;

    @Autowired
    MemberRepository memberrepos;

    @Autowired
    ProgramRepository programrepos;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Reactions> findAll(){
        List<Reactions> list = new ArrayList<>();

        reactionrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);

        return list;
    }

    @Override
    public Reactions findReactionById(long id){
        return reactionrepos.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Reaction id " + id + " not found!"));
    }

    @Override
    public Reactions findByName(String name){
        Reactions re = reactionrepos.findByEmojinameIgnoreCase(name);

        if(re != null){
            return re;
        } else{
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public Reactions save(Reactions reaction){
        return reactionrepos.save(reaction);
    }

    @Transactional
    @Override
    public void deleteAll(){
        reactionrepos.deleteAll();
    }

    @Transactional
    @Override
    public Reactions update(long id, Reactions reaction){
        if(reaction.getEmojiname() == null){
            throw new ResourceNotFoundException("No reaction name found to update!");
        }
      
        Reactions newReaction = findReactionById(id);
        reactionrepos.updateEmojiname(userAuditing.getCurrentAuditor()
                        .get(),
                id, reaction.getEmojiname());

        return findReactionById(id);
    }
}
