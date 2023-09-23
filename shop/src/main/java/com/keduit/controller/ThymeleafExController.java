package com.keduit.controller;

import com.keduit.constant.ItemSellStatus;
import com.keduit.dto.ItemDTO;
import com.keduit.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {

    @GetMapping("/ex01")
    public String thymeleafEx01(Model model){
        model.addAttribute("data", "타임리프 컨트롤러에서 보냈음 (예제 페이지 1)");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafEx02(Model model){
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemDetail("상품 상세 설명");
        itemDTO.setItemNm("테스트 상품1");
        itemDTO.setPrice(10000);
        itemDTO.setRegTime(LocalDateTime.now());

        model.addAttribute("DTO", itemDTO);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping("/ex03")
    public String thymeleafEx03(Model model){
        List<ItemDTO> list = new ArrayList<>();

        for (int i=1; i<=21; i++){
            ItemDTO item = new ItemDTO();
            item.setId((long) i);
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 * i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            if (i < 11){
                item.setSellStatCd("SELL");
            }else {
                item.setSellStatCd("SOLDOUT");
            }
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            list.add(item);
        }

        model.addAttribute("itemDTOList", list);

        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex04")
    public String thymeleafEx04(Model model){
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for (int i=1; i<=21; i++){
            ItemDTO item = new ItemDTO();
            item.setId((long) i);
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 * i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            if (i < 11){
                item.setSellStatCd("SELL");
            }else {
                item.setSellStatCd("SOLDOUT");
            }
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemDTOList.add(item);
        }

        model.addAttribute("itemDTOList", itemDTOList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping("/ex05")
    public String thymeleafEx05(){
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping("/ex06")
    public String thymeleafEx06(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping("/ex07")
    public String thymeleafEx07(){
        return "thymeleafEx/thymeleafEx07";
    }
}
