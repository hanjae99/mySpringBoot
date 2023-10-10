package com.keduit.repository;

import com.keduit.dto.CartDetailDTO;
import com.keduit.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    // 쿼리문에 CartDetailDTO 의 생성자를 이용하여 바로 반환받게끔 설정
    @Query("select new com.keduit.dto.CartDetailDTO(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgYn = 'Y' " +
            "order by ci.regTime desc")
    List<CartDetailDTO> findCartDetailDTOList(Long cartId);
}
