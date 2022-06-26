package com.shop.myshop.controller;

import com.shop.myshop.Entity.Board;
import com.shop.myshop.Entity.BuyList;
import com.shop.myshop.Entity.Item;
import com.shop.myshop.Entity.User;
import com.shop.myshop.Repository.BuyListRepository;
import com.shop.myshop.Repository.ItemRepository;
import com.shop.myshop.Repository.UserRepository;
import com.shop.myshop.Utils.Message;
import com.shop.myshop.VO.BuyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BuyListRepository buyListRepository;

    /**
     * 상세 페이지 이동
     * @return 상세 페이지 이동
     */
    @GetMapping(value = "/detail/{itemNo}")
    public String goItemDetail(@PathVariable("itemNo") Long itemNo, Model model, HttpSession session) {

        System.out.println("----상품 상세 이동----");

        Item item = itemRepository.findByItemNo(itemNo);

        Object userObj = session.getAttribute("user");

        if (!Objects.isNull(userObj)){
            User loginUser = (User) userObj;
            User user = userRepository.findByUserNo(loginUser.getUserNo());

            model.addAttribute("buyer", user);
        }
        else{

            User user = User.builder()
                    .userNo(new Long(0))
                    .userId("admin")
                    .userNm("admin")
                    .userPoint(999999)
                    .build();

            model.addAttribute("buyer", user);
        }

        model.addAttribute("item", item);

        return "thymeleaf/item/detail";
    }

    /**
     * 상품 구매
     * @return 상품 리스트
     */
    @PostMapping(value = "/buy")
    public ResponseEntity<Message> itemBuy(@RequestBody BuyVO buyVO, HttpSession session) {

        System.out.println("----상품 구매----");

        Message msg = new Message();

        // 관리자는 목록안남김
        if (buyVO.getUserNo().equals(0)){
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        //Item itemInfo = itemRepository.findByItemNo(buyVO.getItemNo());
        User userInfo = userRepository.findByUserNo(buyVO.getUserNo());

        Integer point;
        Integer usePoint;
        Integer plusPoint = buyVO.getPayPrice() * 5 / 100;

        // 마일리지 결제시
        if (buyVO.getIsUsePoint()){
            point = userInfo.getUserPoint() - buyVO.getUsePoint() + plusPoint;
            usePoint = buyVO.getUsePoint();
        }
        // 전액 실결제시
        else{
            point = userInfo.getUserPoint() + plusPoint;
            usePoint = 0;

        }

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        User user = User.builder()
                .userNo(userInfo.getUserNo())
                .userId(userInfo.getUserId())
                .userPw(userInfo.getUserPw())
                .userNm(userInfo.getUserNm())
                .userAddress(userInfo.getUserAddress())
                .userAge(userInfo.getUserAge())
                .userPhone(userInfo.getUserPhone())
                .userSex(userInfo.getUserSex())
                .userPoint(point)
                .indate(userInfo.getIndate())
                .build();

        BuyList buyList = BuyList.builder()
                .itemNo(buyVO.getItemNo())
                .userNo(buyVO.getUserNo())
                .payPrice(buyVO.getPayPrice())
                .usePoint(usePoint)
                .indate(dateTime)
                .build();

        userRepository.save(user);
        buyListRepository.save(buyList);

        msg.setMessage("결제가 완료되었습니다.");

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
