package com.shopping.repository;

import com.shopping.domain.items.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

//    @Query("SELECT i FROM Item i WHERE i.name LIKE %:name%") // 이거 문법오류인가본데
//    Page<Item> pagingItem(@Param("name") String name, PageRequest pageRequest);
}
