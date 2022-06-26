package com.shop.myshop.Repository;

import com.shop.myshop.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemNo(Long itemNo);
}
