package com.shop.myshop.controller;

import com.shop.myshop.Entity.BuyList;
import com.shop.myshop.Entity.Item;
import com.shop.myshop.Entity.User;
import com.shop.myshop.Repository.BuyListRepository;
import com.shop.myshop.Repository.ItemRepository;
import com.shop.myshop.Repository.UserRepository;
import com.shop.myshop.Utils.Message;
import com.shop.myshop.VO.BuyVO;
import com.shop.myshop.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/users/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    BuyListRepository buyListRepository;

    @GetMapping(value = "/signUp")
    public String goSignUp() {
        System.out.println("회원가입 페이지 이동");

        return "thymeleaf/users/signUp";
    }

    @PostMapping(value = "/signUp")
    public ResponseEntity<Message> signUp(@RequestBody UserVO userVO){

        System.out.println("회원가입 진행중");

        Message msg = new Message();

        String id = userVO.getUserId();
        String pw = userVO.getUserPw();
        String nm = userVO.getUserNm();
        String address = userVO.getUserAddress();
        String phone = userVO.getUserPhone();
        Integer age = userVO.getUserAge();
        String sex = userVO.getUserSex();

        if (sex.equals("남자"))  sex = "0";
        else                    sex = "1";

        User overlapUser = userRepository.findByUserId(id);

        if (!Objects.isNull(overlapUser)){
            msg.setMessage("중복된 ID입니다. 다른 ID를 입력해주세요.");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        User user = User.builder()
                .userId(id)
                .userPw(pw)
                .userNm(nm)
                .userAddress(address)
                .userPhone(phone)
                .userAge(age)
                .userSex(sex)
                .userPoint(10000)
                .indate(dateTime)
                .build();

        userRepository.save(user);

        msg.setMessage("회원가입 성공하셨습니다.");
        msg.setData("success");

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    public String goLogin() {

        System.out.println("로그인 페이지 이동");

        return "thymeleaf/users/login";
    }

    @PostMapping(value = "/loginProcess")
    public ResponseEntity<Message> loginProcess(@RequestBody UserVO userVO, HttpSession session){

        System.out.println("로그인 진행중...");

        Message msg = new Message();

        String id = userVO.getUserId();
        String pw = userVO.getUserPw();

        User user = userRepository.findByUserIdAndUserPw(id, pw);

        System.out.println(user);

        if (id.equals("admin") || pw.equals("admin")){
            msg.setMessage("로그인 성공하셨습니다.");
            msg.setData("admin");

            session.setAttribute("admin", "admin");

        }
        else if (!Objects.isNull(user)){
            msg.setMessage("로그인 성공하셨습니다.");
            msg.setData(user);

            session.setAttribute("user", user);
        }
        else{
            msg.setMessage("ID, PW를 확인해주세요.");
            msg.setData(null);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    @PostMapping(value = "/logout")
    public ResponseEntity<Message> logoutProcess(HttpSession session){
        
        System.out.println("로그아웃 진행중...");

        Message msg = new Message();

        session.removeAttribute("user");
        session.removeAttribute("admin");

        msg.setMessage("로그아웃 성공하셨습니다.");
        return new ResponseEntity<>(msg, HttpStatus.OK);

    }

    @GetMapping(value = "/buyList")
    public String buyList(HttpSession session, Model model){

        Message msg = new Message();

        Object userObj = session.getAttribute("user");

        if (Objects.isNull(userObj)){
            return "thymeleaf/users/buyList";
        }

        List<BuyList> buyLists = buyListRepository.findAll();
        User user = (User) userObj;

        List<BuyVO> result = new ArrayList<>();

        buyLists.forEach(x -> {
            if (x.getUserNo().equals(user.getUserNo())){
                Item item = itemRepository.findByItemNo(x.getItemNo());
                BuyVO buyVO = new BuyVO();
                buyVO.setListNo(x.getListNo());
                buyVO.setItemNo(x.getItemNo());
                buyVO.setItemNm(item.getItemNm());
                buyVO.setUserNo(x.getUserNo());
                buyVO.setPayPrice(x.getPayPrice());
                buyVO.setUsePoint(x.getUsePoint());
                buyVO.setIndate(x.getIndate());
                result.add(buyVO);
            }
        });

        // ITEM 처리하기(이름 및 정보)

        model.addAttribute("buyList", result);

        return "thymeleaf/users/buyList";
    }

    @GetMapping(value = "/loginCheck")
    public ResponseEntity<Message> loginCheck(HttpSession session){

        Message msg = new Message();

        Object user = session.getAttribute("user");
        Object admin = session.getAttribute("admin");

        if (Objects.isNull(user) && Objects.isNull(admin)){
            msg.setMessage("로그인이 먼저 필요합니다.");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        else{
            msg.setData("sucess");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
    }

}
