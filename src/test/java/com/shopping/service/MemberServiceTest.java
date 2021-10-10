package com.shopping.service;

import com.shopping.controller.form.ChangePasswordForm;
import com.shopping.controller.form.MemberJoinForm;
import com.shopping.domain.Member;
import com.shopping.enums.Grade;
import com.shopping.exception.NoSuchMemberException;
import com.shopping.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void checkDuplicateId() {

        // given
        Member member = Member.createMember("kim", "kim123", "k1235#");
        em.persist(member);

        // when
        boolean result1 = memberService.checkDuplicateId("kim123");
        boolean result2 = memberService.checkDuplicateId("kim123pppp");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void join() {

        // given
        MemberJoinForm memberJoinForm = new MemberJoinForm("kim5989", "kim3333#", "park");

        // when
        Long joinedMemberId = memberService.join(memberJoinForm);

        // then
        Optional<Member> om = memberRepository.findById(joinedMemberId);
        Member member = om.orElseThrow(() -> new NoSuchMemberException());

        assertThat(member.getSaleAvailable()).isFalse();
        assertThat(member.getGrade()).isEqualTo(Grade.USER);
        assertThat(member.getLoginId()).isEqualTo("kim5989");

    }

    @Test
    void passwordChange() throws Exception {

        // given
        MemberJoinForm memberJoinForm = new MemberJoinForm("kim5989", "kim3333#", "park");
        Long joinedMemberId = memberService.join(memberJoinForm);

        ChangePasswordForm changePasswordForm
                = new ChangePasswordForm(joinedMemberId, "kim3333#", "new333@", "new333@");

        // when
        Long changedMemberId = memberService.passwordChange(changePasswordForm);

        // then
        Optional<Member> om = memberRepository.findById(changedMemberId);
        Member findMember = om.orElseThrow(() -> new NoSuchMemberException());

        assertThat(joinedMemberId).isEqualTo(changedMemberId);
        assertThat(findMember.getLoginId()).isEqualTo("kim5989");

    }

}