package com.shop.myshop.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_nm")
    private String userNm;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_sex")
    private String userSex;

    @Column(name = "user_point")
    private Integer userPoint;

    @Column(name = "indate")
    private LocalDateTime indate;

    @Builder
    public User(Long userNo, String userId, String userPw, String userNm, String userAddress, String userPhone, Integer userAge, String userSex, Integer userPoint, LocalDateTime indate){
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userAge = userAge;
        this.userSex = userSex;
        this.userPoint = userPoint;
        this.indate = indate;
    }
}
