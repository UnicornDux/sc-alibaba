package com.tuling.cloud.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "stock-service", path = "/stock")
public interface StockClient {

    @RequestMapping(value = "/decrease", method = RequestMethod.PUT)
    String decreasePrice(@RequestParam("goodId") Integer productId, @RequestParam("price") Float price);

}
