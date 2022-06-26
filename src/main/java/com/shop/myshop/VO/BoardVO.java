package com.shop.myshop.VO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardVO {
    private Long boardNo;
    private String boardTitle;
    private String boardWriter;
    private String boardContent;
    private String indate;
}