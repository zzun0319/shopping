package com.shopping.repository;

import com.shopping.domain.Member;
import com.shopping.enums.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    List<Member> findByGrade(@Param("grade") Grade grade);

    List<Member> findBySaleAvailable(@Param("available") Boolean available);

}
