package com.tuling.cloud.controller;

import com.tuling.cloud.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StackController {

    @Autowired
    private StockService stockService;

    @GetMapping("/reduce")
    public String reduceStack(){
        return "减少库存成功";
    }

    @PutMapping("/decrease")
    public String decreasePrice(Integer goodId, Float price){
        stockService.decreasePrice(goodId, price);
        return "修改价格成功";
    }
}
