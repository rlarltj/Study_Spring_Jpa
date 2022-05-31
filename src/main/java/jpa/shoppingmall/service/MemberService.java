package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Address;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validDuplicateMember(Member member) {
        List<Member> list = memberRepository.findByName(member.getUsername());
        if(!list.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void sampleData() {
        Member member1 = createMember("kim", 20, "seoul", "mapo-gu", "123-123");
        Member member2 = createMember("Lee", 30, "busan", "street", "456-456");

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    private Member createMember(String name, int age, String city, String street, String zipcode) {
        Member member = new Member();
        member.setUsername(name);
        member.setAge(age);
        member.setAddress(new Address(city, street, zipcode));
        return member;
    }
}
