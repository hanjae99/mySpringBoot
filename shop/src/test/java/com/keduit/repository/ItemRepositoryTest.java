package com.keduit.repository;

import com.keduit.constant.ItemSellStatus;
import com.keduit.entity.Item;
import com.keduit.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Querydsl 테스트")
    public void querydslTest(){
        this.createItemList();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory
                .select(qItem)
                .from(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        for (Item item : itemList){
            System.out.println("item = " + item);
        }
    }

    @Test
    @DisplayName("Querydsl 테스트2")
    public void querydslTest2(){
        this.createItemList();

        BooleanBuilder builder = new BooleanBuilder();
        QItem qItem = QItem.item;
        String itemDetail = "테스트";
        int price = 40000;
        String itemSellStat = "SELL";

        builder.and(qItem.itemDetail.like("%" + itemDetail + "%"));
        builder.and(qItem.price.gt(price));

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            builder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult =
                itemRepository.findAll(builder, pageable);
        System.out.println("전체 페이지 수: " + itemPagingResult.getTotalPages());
        System.out.println("현재 페이지 번호: " + itemPagingResult.getNumber());
        System.out.println("조회한 전체 상품 수: " + itemPagingResult.getTotalElements());
        System.out.println("현재 페이지의 게시물 수: " + itemPagingResult.getSize());

        List<Item> resultItemList = itemPagingResult.getContent();
        for (Item resultItem : resultItemList){
            System.out.println("조회한 아이템 = " + resultItem);
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세설명 입니다");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println("savedItem = " + savedItem);
    }


    @Test
    public void createItemList(){
        for (int i=1; i<=21; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 * i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            if (i < 11){
                item.setItemSellStatus(ItemSellStatus.SELL);
                item.setStockNumber(1000);
            }else {
                item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
                item.setStockNumber(0);
            }
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    public void findByItemNmTest(){
        this.createItemList();

        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList){
            System.out.println("item = " + item);
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();

        List<Item> itemList =
                itemRepository.findByItemNmOrItemDetail("테스트 상품2", "테스트 상품 상세 설명3");

        for (Item item : itemList){
            log.info("-------------------------");
            log.info(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();

        List<Item> itemList = itemRepository.findByPriceLessThan(40000);

        for (Item item : itemList){
            log.info("findByPriceLessThan...");
            log.info(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan + 정렬 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemList();

        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(60000);

        for (Item item : itemList){
            log.info("findByPriceLessThanOrderByPriceDesc...");
            log.info(item.toString());
        }
    }

    @Test
    @DisplayName("@Query 를 이용한 상품조회 테스트")
    public void findItemDetailTest(){
//        this.createItemList();

        Sort sort1 = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(1, 10, sort1);

        List<Item> itemList = itemRepository.findByItemDetail("상품 상세", pageable);

        for (Item item : itemList){
            log.info("@Query...");
            log.info(item.toString());
        }
    }

    @Test
    public void testSelect(){
        this.createItemList();
        Long id = 10L;

        Optional<Item> result = itemRepository.findById(id);
        log.info("-----------------");
        if (result.isPresent()){
            Item item = result.get();
            log.info(item.toString());
        }
    }

    @Transactional
    @Test
    public void testSelect2(){
//        this.createItemList();

        Long id = 10L;

        Item item = itemRepository.getOne(id);
        log.info("-------------------");
        log.info(item.toString());
    }

    @Test
    public void testUpdate(){
        Item item = Item.builder().id(20L).itemNm("상품명 수정!")
                .itemDetail("상품 내용 수정!").price(36000).build();
        log.info(itemRepository.save(item).toString());
    }

    @Test
    public void testDelete(){
        Long id = 20L;

        itemRepository.deleteById(id);
    }

//    기본 페이징
    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(2, 10);

        Page<Item> result = itemRepository.findAll(pageable);

        log.info(result.toString());

        for (Item item : result.getContent()){
            log.info(item.toString());
        }
    }

//    id 기준으로 정렬
    @Test
    public void testSort(){
        Sort sort1 = Sort.by("id").descending();

        Pageable pageable = PageRequest.of(1, 10, sort1);
        Page<Item> result = itemRepository.findAll(pageable);

        log.info(result.toString());
        for (Item item : result.getContent()){
            log.info(item.toString());
        }
    }
}