package jpa.shoppingmall.repository;

import jpa.shoppingmall.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", name)
                .getResultList();
    }

    public Optional<Member> findMemberByLoginId(String loginId){
        log.info("id = {}", loginId);
        List<Member> members =  em.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();

        return members.stream().findAny();
    }

//    public Optional<String> password(String loginId){
//        List<String> password = em.createQuery("select m.password from Member m where m.loginId = :loginId")
//                .setParameter("loginId", loginId)
//                .getResultList();
//
//        return  Stream.of(password).findAny();
//    }
}
