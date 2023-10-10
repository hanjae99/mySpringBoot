package com.keduit.entity;

import com.keduit.constant.ItemSellStatus;
import com.keduit.dto.ItemFormDTO;
import com.keduit.exception.OutOfStockException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
//@Table(name = "tbl_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고 수량

    @Column(nullable = false)
    @Lob
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    public void updateItem(ItemFormDTO itemFormDTO){
        this.itemNm = itemFormDTO.getItemNm();
        this.price = itemFormDTO.getPrice();
        this.stockNumber = itemFormDTO.getStockNumber();
        this.itemDetail = itemFormDTO.getItemDetail();
        this.itemSellStatus = itemFormDTO.getItemSellStatus();
    }

    // 주문 시 재고 수량에서 빼기
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }

        this.stockNumber = restStock;
    }

    // 주문 취소 시 재고 수량 다시 더하기
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }
}
