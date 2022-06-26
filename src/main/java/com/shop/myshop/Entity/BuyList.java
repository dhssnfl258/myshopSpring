package com.shop.myshop.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
@Table(name = "buy_list_tb")
public class BuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_no")
    private Long listNo;

    @Column(name = "item_no")
    private Long itemNo;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "pay_price")
    private Integer payPrice;

    @Column(name = "use_point")
    private Integer usePoint;

    @Column(name = "indate")
    private LocalDateTime indate;

    @Builder
    public BuyList(Long itemNo, Long userNo, Integer payPrice, Integer usePoint, LocalDateTime indate){
        this.itemNo = itemNo;
        this.userNo = userNo;
        this.payPrice = payPrice;
        this.usePoint = usePoint;
        this.indate = indate;
    }
}
