package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Reactions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;


public interface ReactionRepository extends CrudRepository<Reactions, Long>
{
    Reactions findByEmojinameIgnoreCase(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE reactions SET emojiname = :name, last_modified_by = :uname, last_modified_date = CURRENT_TIMESTAMP WHERE reactionid = :reactionid",
            nativeQuery = true)
    void updateEmojiname(String uname, long reactionid, String name);
}
