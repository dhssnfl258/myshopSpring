package com.shop.myshop.controller;

import com.shop.myshop.Entity.Item;
import com.shop.myshop.Repository.ItemRepository;
import com.shop.myshop.Utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    ItemRepository itemRepository;

    /**
     * 메인화면
     * @return 메인화면으로 이동
     */
    @GetMapping(value = "/")
    public String firstConnect() {

//        System.out.println("★로그인 세션 테스트 : " + authentication.getPrincipal().toString());

        System.out.println("----메인화면 접속----");

        return "thymeleaf/main";
    }

    @GetMapping(value = "getItems")
    public ResponseEntity<Message> getItems(){

        Message msg = new Message();

        List<Item> items = itemRepository.findAll();

        msg.setData(items);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
