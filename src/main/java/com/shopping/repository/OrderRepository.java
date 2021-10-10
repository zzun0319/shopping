package com.shopping.repository;

import com.shopping.domain.Member;
import com.shopping.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.member WHERE o.member = :member")
    List<Order> findOrdersByMember(@Param("member") Member member);
}
