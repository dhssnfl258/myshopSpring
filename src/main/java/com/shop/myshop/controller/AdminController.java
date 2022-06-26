package com.shop.myshop.controller;

import com.shop.myshop.Entity.Board;
import com.shop.myshop.Entity.Item;
import com.shop.myshop.Repository.ItemRepository;
import com.shop.myshop.Utils.Message;
import com.shop.myshop.VO.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ItemRepository itemRepository;

    /**
     * 관리자 전용 상품 리스트
     * @return 상품 리스트로 이동
     */
    @GetMapping(value = "/list")
    public String adminItemList(String word, Model model, HttpSession session) {

        System.out.println("----관리자 상품 리스트 접속----");

        Object isAdmin = session.getAttribute("admin");

        List<Item> items = itemRepository.findAll();

        List<Item> result = new ArrayList<>();

        items.forEach(x -> {
            if (Objects.isNull(word) || x.getItemNm().contains(word)){
                result.add(x);
            }
        });

        model.addAttribute("list", result);

        return "thymeleaf/admin/list";
    }

    /**
     * 상품 추가 페이지 이동
     * @return 상품 추가 페이지 이동
     */
    @GetMapping(value = "/add")
    public String goItemAdd() {

        System.out.println("----상품 추가 페이지 접속----");

        return "thymeleaf/admin/add";
    }

    /**
     * 상품 추가
     * @return 상품 추가
     */
    @PostMapping(value = "/add")
    public ResponseEntity<Message> itemAdd(
            @RequestParam("nm") String nm, @RequestParam("content") String content, @RequestParam("price") Integer price, @RequestParam("file") MultipartFile file
    ) throws Exception{

        System.out.println("----상품 추가----");

        Message msg = new Message();

//        String absolutePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\";
//        String absolutePath = new File("").getAbsolutePath() + "\\";
        String absolutePath = "/Users/dhssnfl258/Desktop/12184898_shop/fileUpload/";

        String path = "images/";
        File saveFile = new File(path);

        if (!saveFile.exists()) saveFile.mkdirs();

        String type = file.getContentType();
        String originalFileExtension;

        if (ObjectUtils.isEmpty(type)) {
            msg.setMessage("파일 확장자명이 없습니다.");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        if (type.contains("image/jpeg")) originalFileExtension = ".jpg";
        else if (type.contains("image/png")) originalFileExtension = ".png";
        else if (type.contains("image/gif")) originalFileExtension = ".gif";
        else {
            msg.setMessage("지원하지 않는 파일형식입니다.");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }


        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        String fileTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

        Item item = Item.builder()
                .itemNm(nm)
                .itemContent(content)
                .itemPrice(price)
                .itemFileNm(file.getOriginalFilename())
                .indate(dateTime)
                .modate(dateTime)
                .build();

        Item savedItem = itemRepository.save(item);

        Item fileSave = Item.builder()
                .itemNo(savedItem.getItemNo())
                .itemNm(nm)
                .itemContent(content)
                .itemPrice(price)
                .itemFileNm(savedItem.getItemNo()  + originalFileExtension)
                .indate(dateTime)
                .modate(dateTime)
                .build();

        itemRepository.save(fileSave);

//        saveFile = new File(absolutePath + path + "/" + savedItem.getItemNo()  + "_" + fileTime + originalFileExtension);
        saveFile = new File(absolutePath + path + "/" + savedItem.getItemNo()  + originalFileExtension);
        file.transferTo(saveFile);

        msg.setMessage("저장을 성공했습니다.");
        msg.setData("success");

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * 상세 페이지 이동
     * @return 상세 페이지 이동
     */
    @GetMapping(value = "/detail/{itemNo}")
    public String goItemDetail(@PathVariable("itemNo") Long itemNo, Model model) {

        System.out.println("----게시판 상세 이동----");

        Item item = itemRepository.findByItemNo(itemNo);

        model.addAttribute("item", item);

        return "thymeleaf/admin/detail";
    }

    /**
     * 상품 삭제
     * @return 상품 목록 이동
     */
    @DeleteMapping(value = "/delete/{itemNo}")
    public String itemDelete(@PathVariable("itemNo") Long itemNo) {

        System.out.println("----상품 삭제----");

        Item item = itemRepository.findByItemNo(itemNo);

        itemRepository.deleteById(itemNo);

        String absolutePath = "/Users/dhssnfl258/Desktop/12184898_shop/fileUpload/images/";

        String[] types = item.getItemFileNm().split("\\.");
        String type = types[types.length - 1];
        File file = new File(absolutePath + itemNo + "." + type);
        if (file.exists()){
            file.delete();
        }

        return "thymeleaf/admin/list";
    }

    /**
     * 상품 수정 페이지 이동
     * @return 상품 수정 페이지 이동
     */
    @GetMapping(value = "/update/{itemNo}")
    public String goItemUpdate(@PathVariable("itemNo") Long itemNo, Model model) {

        System.out.println("----상품 수정 페이지----");

        Item item = itemRepository.findByItemNo(itemNo);

        model.addAttribute("item", item);

        return "thymeleaf/admin/update";
    }

    /**
     * 상품 수정
     * @return 상품 수정
     */
    @PutMapping(value = "/update")
    public ResponseEntity<Message> itemUpdate(
            @RequestParam("no") Long no, @RequestParam("nm") String nm, @RequestParam("content") String content,
            @RequestParam("price") Integer price, @RequestParam("indate") String indate, @RequestParam("fileName") String fileName, @RequestParam(value = "newFile", required = false) MultipartFile newFile
    ) throws Exception{

        System.out.println("----상품 수정----");

        Message msg = new Message();

        String saveFileName;

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = localDateTime.format(formatter);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        String indateTimeString = indate.replace('T', ' ');
        LocalDateTime indateTime = LocalDateTime.parse(indateTimeString, formatter);


        // 변경할 이미지 있으면 교체
        if (!Objects.isNull(newFile)) {

//            String absolutePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\";
//            String absolutePath = new File("").getAbsolutePath() + "\\";
            String absolutePath = "/Users/dhssnfl258/Desktop/12184898_shop/fileUpload/";

            String path = "images/";
            File saveFile = new File(path);

            if (!saveFile.exists()) saveFile.mkdirs();

            String type = newFile.getContentType();
            String originalFileExtension;

            if (ObjectUtils.isEmpty(type)) {
                msg.setMessage("파일 확장자명이 없습니다.");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }

            if (type.contains("image/jpeg")) originalFileExtension = ".jpg";
            else if (type.contains("image/png")) originalFileExtension = ".png";
            else if (type.contains("image/gif")) originalFileExtension = ".gif";
            else {
                msg.setMessage("지원하지 않는 파일형식입니다.");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }


            String fileTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
            saveFileName = no + originalFileExtension;

            saveFile = new File(absolutePath + path + "/" + no  + originalFileExtension);
            newFile.transferTo(saveFile);
        }
        else{
            saveFileName = fileName;
        }


        Item item = Item.builder()
                .itemNo(no)
                .itemNm(nm)
                .itemContent(content)
                .itemPrice(price)
                .itemFileNm(saveFileName)
                .indate(indateTime)
                .modate(dateTime)
                .build();

        itemRepository.save(item);

        msg.setMessage("저장을 성공했습니다.");
        msg.setData("success");

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
