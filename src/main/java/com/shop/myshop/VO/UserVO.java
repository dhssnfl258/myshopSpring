package com.shop.myshop.VO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserVO {
    private Long userNo;
    private String userId;
    private String userPw;
    private String userNm;
    private String userAddress;
    private String userPhone;
    private Integer userAge;
    private String userSex;
    private Integer userPoint;
    private LocalDateTime indate;
}
