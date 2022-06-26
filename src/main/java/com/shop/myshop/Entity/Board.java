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
@Table(name = "board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "board_writer")
    private String boardWriter;

    @Column(name = "board_content")
    private String boardContent;

    @Column(name = "indate")
    private LocalDateTime indate;

    @Column(name = "modate")
    private LocalDateTime modate;

    @Builder
    public Board(Long boardNo, String boardTitle, String boardWriter, String boardContent, LocalDateTime indate, LocalDateTime modate){
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardContent = boardContent;
        this.indate = indate;
        this.modate = modate;
    }
}
