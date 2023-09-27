package com.keduit.dto;

import com.keduit.constant.ItemSellStatus;
import com.keduit.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDTO {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력값입니다")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력값입니다")
    private Integer price;

    @NotBlank(message = "상세정보는 필수 입력값입니다")
    private String itemDetail;

    @NotNull(message = "재고 수량은 필수 입력값입니다")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDTO of(Item item){
        return modelMapper.map(item, ItemFormDTO.class);
    }
}
