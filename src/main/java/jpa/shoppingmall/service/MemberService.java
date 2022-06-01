package jpa.shoppingmall.service;

import jpa.shoppingmall.domain.Address;
import jpa.shoppingmall.domain.GRADE;
import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        if(validDuplicateLoginId(member))
        return memberRepository.save(member);

        else throw new IllegalStateException("이미 사용중인 아이디입니다.");
    }

//    private void validDuplicateMember(Member member) {
//        List<Member> list = memberRepository.findByName(member.getUsername());
//        if(!list.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }

    private boolean validDuplicateLoginId(Member member){
        Optional<Member> findMember = memberRepository.findMemberByLoginId(member.getLoginId());
        return findMember.isEmpty();
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
        Member member1 = createMember("kim", "test1", "1234" , 20, "seoul", "mapo-gu", "123-123", GRADE.ADMIN);
        Member member2 = createMember("Lee", "test2", "1234", 30, "busan", "street", "456-456", GRADE.GUEST);

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    private Member createMember(String name, String loginId, String password, int age, String city, String street, String zipcode, GRADE grade) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setUsername(name);
        member.setAge(age);
        member.setAddress(new Address(city, street, zipcode));
        member.setGrade(grade);
        return member;
    }
}
