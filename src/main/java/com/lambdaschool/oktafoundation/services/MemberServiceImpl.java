package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.models.Reactions;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "memberService")
public class MemberServiceImpl implements MemberService
{
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReactionsService reactionsService;

    @Override
    public List<Member> findAll()
    {
        List<Member> list = new ArrayList<>();
        memberRepository.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Member saveNewMember(String newMember)
    {

        Member isCurrentMember = memberRepository.findMemberByMemberid(newMember);
        if ( isCurrentMember == null )
        {
            Member addMember = new Member();
            addMember.setMemberid(newMember);
            return addMember;
        }
        return isCurrentMember;
    }

    @Override
    public Member save(Member member) {
        Member newMember = new Member();

        if (member.getId() != 0) {
            memberRepository.findById(member.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Member id " + member.getMemberid() + " not found"));
            newMember.setId(member.getId());
        }

        // if the member id is already in the database, return it, no new member is created
//        var temp = memberRepository.findMemberByMemberid(member.getMemberid());
//        if (temp.isPresent()) {
//            return temp.get();
//        }

        newMember.setMemberid(member.getMemberid());

        // Relationships
        newMember.getMemberReactons().clear();
        for (Reactions mr : member.getMemberReactons()) {
            Reactions addReactions = reactionsService.findReactionById(mr.getReactionid());
            newMember.setMemberReactons(member.getMemberReactons());
        }
        return memberRepository.save(newMember);
    }

    @Override
    public List<Member> findByIdContaining(String partialmemberId)
    {
        return memberRepository.findMembersByMemberidContaining(partialmemberId);
    }

    @Transactional
    @Override
    public List<Member> saveNewMembers(InputStream stream) throws IOException
    {
        List<Member> csvMembers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String member;
        // removes header line from CSV file
        String headerLine = reader.readLine();

        // adds validation check to make sure CSV file contains the memberid header
        if (! headerLine.equalsIgnoreCase("memberid") )
        {
            throw new ResourceNotFoundException("CSV file must contain header memberid");
        }

        while((member = reader.readLine())!= null)
        {
            // removes any quotes if needed from ends of memberid in CSV file
            member = member.replaceAll("^\"|\"$", "");

            Member currentMember = memberRepository.findMemberByMemberid(member);
            if ( currentMember == null )
            {
                Member newMember = new Member();
                newMember.setMemberid(member);
                Member addedMember = save(newMember);
                csvMembers.add(addedMember);
            }
            else
            {
                csvMembers.add(currentMember);
            }

        }
        // all members are returned to allow frontend to use the array list
        // to generate QR codes
        return csvMembers;
    }

    @Override
    public Member findMemberByJavaId(long id)
    {
        return memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Member id " + id + "Not Found"));
    }
    @Override
    public Member findMemberByStringId(String memberId)
    {
        Member mm = memberRepository.findMemberByMemberid(memberId);
        if (mm == null)
        {
            throw new ResourceNotFoundException("Member Id" + memberId + "Not Found");
        }
        return mm;
    }

    @Override
    public void delete(long id)
    {
        memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Member id" + id + " Not Found"));
        memberRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
