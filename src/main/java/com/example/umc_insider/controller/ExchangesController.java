package com.example.umc_insider.controller;

import com.example.umc_insider.domain.Exchanges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc_insider.service.ExchangesService;
import com.example.umc_insider.dto.request.PostExchangesReq;


@RestController
@RequestMapping("/exchanges")
public class ExchangesController {

    private final ExchangesService exchangesService;

    @Autowired
    public ExchangesController(ExchangesService exchangesService) {
        this.exchangesService = exchangesService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerExchange(@RequestBody PostExchangesReq request) {
        Exchanges exchange = exchangesService.registerExchange(request.getMineGoodsId(), request.getExchangeItem());
        return ResponseEntity.ok("Exchange registered with ID: " + exchange.getId());
    }

    @PostMapping("/complete/{exchangeId}")
    public ResponseEntity<String> completeExchange(@PathVariable Long exchangeId) {
        Exchanges exchange = exchangesService.completeExchange(exchangeId);
        return ResponseEntity.ok("Exchange completed with ID: " + exchange.getId());
    }
}
