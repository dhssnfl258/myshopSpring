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
@Table(name = "item_tb")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_no")
    private Long itemNo;

    @Column(name = "item_nm")
    private String itemNm;

    @Column(name = "item_content")
    private String itemContent;

    @Column(name = "item_price")
    private Integer itemPrice;

    @Column(name = "item_file_nm")
    private String itemFileNm;

    @Column(name = "indate")
    private LocalDateTime indate;

    @Column(name = "modate")
    private LocalDateTime modate;

    @Builder
    public Item(Long itemNo, String itemNm, String itemContent, Integer itemPrice, String itemFileNm, LocalDateTime indate, LocalDateTime modate){
        this.itemNo = itemNo;
        this.itemNm = itemNm;
        this.itemContent = itemContent;
        this.itemPrice = itemPrice;
        this.itemFileNm = itemFileNm;
        this.indate = indate;
        this.modate = modate;
    }
}
