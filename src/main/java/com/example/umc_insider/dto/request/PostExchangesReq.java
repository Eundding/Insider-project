package com.example.umc_insider.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.example.umc_insider.domain.Goods;

@Getter
@AllArgsConstructor
public class PostExchangesReq {
    private Goods mineGoodsId;
    private String exchangeItem;
//    private Goods yoursGoods;
    private String status;
//    private Long mineGoodsId;
//    private Long yoursGoodsId;
}
