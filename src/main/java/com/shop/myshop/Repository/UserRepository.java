package com.shop.myshop.Repository;

import com.shop.myshop.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNo(Long no);
    User findByUserId(String id);
    User findByUserIdAndUserPw(String userId, String userPw);
}
