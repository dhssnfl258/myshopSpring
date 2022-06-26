package com.shop.myshop.VO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemVO {

    private Long itemNo;
    private String itemNm;
    private String itemContent;
    private Integer itemPrice;
    private String fileNm;
    private LocalDateTime indate;
    private LocalDateTime modate;
}
