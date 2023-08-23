package com.example.umc_insider.service;

import com.example.umc_insider.repository.GoodsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.umc_insider.repository.ExchangesRepository;
import com.example.umc_insider.domain.Exchanges;
import com.example.umc_insider.domain.Goods;
import org.springframework.stereotype.Service;

@Service
public class ExchangesService {
    private ExchangesRepository exchangesRepository;
    private GoodsRepository goodsRepository;

    @Autowired
    public ExchangesService(ExchangesRepository exchangesRepository, GoodsRepository goodsRepository){
        this.exchangesRepository = exchangesRepository;
        this.goodsRepository = goodsRepository;
    }

//    public Exchanges registerExchange(Long mineGoods, String exchangesItem) {
//        Exchanges exchange = new Exchanges()
//                .registerExchanges(mineGoods, exchangesItem, "교환");
//        return exchangesRepository.save(exchange);


//    @Transactional
//    public Exchanges registerExchange(Long mineGoodsId, String exchangeItem) {
//        // Find the Goods entity by id.
//        Goods mineGoods = goodsRepository.findById(mineGoodsId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid mine_goods_id: " + mineGoodsId));
//
//        // Create a new Exchanges entity.
//        Exchanges exchange = new Exchanges();
//
//        // Set the found Goods entity to the exchange.
//        exchange.setMineGoods(mineGoods);
//        exchange.setExchangeItem(exchangeItem);
//
//        return exchangesRepository.save(exchange);
//    }

    public Exchanges registerExchange(Long mineGoodsId, String exchangesItem) {
        Goods mineGoods = goodsRepository.findById(mineGoodsId)
                .orElseThrow(() -> new EntityNotFoundException("Goods with ID " + mineGoodsId + " not found"));

        Exchanges exchange = new Exchanges()
                .registerExchanges(mineGoods.getId(), exchangesItem, "교환");

        return exchangesRepository.save(exchange);
    }


    public Exchanges completeExchange(Long exchangeId) {
        Exchanges exchange = exchangesRepository.findById(exchangeId)
                .orElseThrow(() -> new EntityNotFoundException("Exchange not found with ID: " + exchangeId));
        exchange.completionExchanges("교환완료");
        return exchangesRepository.save(exchange);
    }
}
