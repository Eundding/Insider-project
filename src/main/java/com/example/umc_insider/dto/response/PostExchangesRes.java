package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostExchangesRes {
    private Long exchangeId;
//    private Long mineGoodsId;
    private Goods mineGoods;
    private String exchangeItem;
//    private Goods yoursGoods;
//    private String status;
}
