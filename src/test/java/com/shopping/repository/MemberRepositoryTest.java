package com.shopping.repository;

import com.shopping.domain.Member;
import com.shopping.enums.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Test
    void findByLoginId() {

        // given
        Member member = Member.createMember("kim", "kim123", "k1235#");
        em.persist(member);

        // when
        Optional<Member> om = memberRepository.findByLoginId("kim123");
        Member findMember = om.orElse(Member.createMember("new", "new123", "new1234!"));

        // then
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    void findByGrade() throws Exception {

        // given
        Member member1 = new Member("name1", "loginid1", "pass123", Grade.VIP, true);
        Member member2 = new Member("name2", "loginid2", "pass123", Grade.USER, true);
        Member member3 = new Member("name3", "loginid3", "pass123", Grade.VIP, false);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when
        List<Member> result = memberRepository.findByGrade(Grade.VIP);

        // then
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    void findBySaleAvailable() throws Exception {

        // given
        Member member1 = new Member("name1", "loginid1", "pass123", Grade.VIP, true);
        Member member2 = new Member("name2", "loginid2", "pass123", Grade.USER, true);
        Member member3 = new Member("name3", "loginid3", "pass123", Grade.VIP, false);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when
        List<Member> result = memberRepository.findBySaleAvailable(false);

        // then
        assertThat(result.size()).isEqualTo(1);

    }

}