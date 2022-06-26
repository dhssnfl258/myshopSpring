package com.shop.myshop.VO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BuyVO {

    private Long listNo;
    private Long itemNo;
    private Long userNo;
    private Integer usePoint;
    private Integer payPrice;
    private Boolean isUsePoint;
    private LocalDateTime indate;
    private String itemNm;
}
