package com.shop.myshop.controller;

import com.shop.myshop.Entity.Board;
import com.shop.myshop.Entity.User;
import com.shop.myshop.Repository.BoardRepository;
import com.shop.myshop.Utils.Message;
import com.shop.myshop.VO.BoardVO;
import com.shop.myshop.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/board/")
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    /**
     * 게시판 리스트
     * @return 게시판 리스트로 이동
     */
    @GetMapping(value = {"/list"})
    public String boardList(String word, Model model) {

        System.out.println("----게시판 리스트 접속----");

        List<Board> boards = boardRepository.findAll();

        List<Board> result = new ArrayList<>();

        boards.forEach(x -> {
            if (Objects.isNull(word) || x.getBoardTitle().contains(word)){
                result.add(x);
            }
        });

        model.addAttribute("list", result);

        return "thymeleaf/board/list";
    }

    /**
     * 글쓰기 페이지 이동
     * @return 글쓰기 페이지 이동
     */
    @GetMapping(value = "/write")
    public String goBoardWrite() {

        System.out.println("----게시판 작성 접속----");

        return "thymeleaf/board/write";
    }

    /**
     * 글쓰기 저장
     * @return 게시판 리스트 이동
     */
    @PostMapping(value = "/write")
    public String boardWrite(@RequestBody BoardVO boardVO) {

        System.out.println("----게시판 작성----");

        String title = boardVO.getBoardTitle();
        String writer = boardVO.getBoardWriter();
        String content = boardVO.getBoardContent();

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        Board board = Board.builder()
                .boardTitle(title)
                .boardWriter(writer)
                .boardContent(content)
                .indate(dateTime)
                .modate(dateTime)
                .build();

        boardRepository.save(board);

        return "thymeleaf/board/list";
    }

    /**
     * 상세 페이지 이동
     * @return 상세 페이지 이동
     */
    @GetMapping(value = "/detail/{boardNo}")
    public String goBoardDetail(@PathVariable("boardNo") Long boardNo, Model model) {

        System.out.println("----게시판 상세 이동----");

        Board board = boardRepository.findByBoardNo(boardNo);

        model.addAttribute("board", board);

        return "thymeleaf/board/detail";
    }

    /**
     * 게시글 삭제
     * @return 게시글 목록 이동
     */
    @DeleteMapping(value = "/delete/{boardNo}")
    public String boardDelete(@PathVariable("boardNo") Long boardNo) {

        System.out.println("----게시판 삭제----");

        boardRepository.deleteById(boardNo);

        return "thymeleaf/board/list";
    }

    /**
     * 게시글 수정 페이지 이동
     * @return 게시글 수정 페이지 이동
     */
    @GetMapping(value = "/update/{boardNo}")
    public String goBoardUpdate(@PathVariable("boardNo") Long boardNo, Model model) {

        System.out.println("----게시판 수정 페이지----");

        Board board = boardRepository.findByBoardNo(boardNo);

        model.addAttribute("board", board);

        return "thymeleaf/board/update";
    }

    /**
     * 게시글 수정
     * @return 게시글 수정
     */
    @PutMapping(value = "/update")
    public ResponseEntity<Message> boardUpdate(@RequestBody BoardVO boardVO, Model model) {

        System.out.println("----게시판 수정----");

        Message msg = new Message();

        Long no = boardVO.getBoardNo();
        String title = boardVO.getBoardTitle();
        String writer = boardVO.getBoardWriter();
        String content = boardVO.getBoardContent();
        String indate = boardVO.getIndate().replace('T', ' ');

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime indateTime = LocalDateTime.parse(indate, formatter);

        Board board = Board.builder()
                .boardNo(no)
                .boardTitle(title)
                .boardWriter(writer)
                .boardContent(content)
                .indate(indateTime)
                .modate(dateTime)
                .build();

        boardRepository.save(board);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
