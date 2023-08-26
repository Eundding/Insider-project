package com.example.umc_insider.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.example.umc_insider.domain.Goods;

@Getter
@AllArgsConstructor
public class PostExchangesReq {
    private Long yoursGoodsId;
    private Long mineGoodsId;
    private String exchangeItem;
    private String status;
}
