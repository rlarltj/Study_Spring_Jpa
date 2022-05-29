package jpa.shoppingmall.domain.service;

import jpa.shoppingmall.domain.Member;
import jpa.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
}
