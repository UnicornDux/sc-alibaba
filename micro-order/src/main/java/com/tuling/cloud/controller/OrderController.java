package com.tuling.cloud.controller;


import com.tuling.cloud.model.order.OrderInfo;
import com.tuling.cloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;

    @GetMapping("/add")
    public String addOrder(){
        log.info("add order success");
        /*
         // 指定实例访问
         String result = restTemplate.getForObject(
                 "http://localhost:8011/stack/reduce",
                 String.class
         );
       */
        // 从注册中心拉取服务
        String result = restTemplate.getForObject(
                "http://stock-service/stock/reduce",
                String.class
        );
        return "下单成功" + result;
    }

    // 内部基于 OpenFeign 客户端调用其他服务
    @PostMapping("/create")
    public String createOrder(@RequestBody OrderInfo orderInfo){

        log.info("create order success");
        // 指定实例访问
        orderService.create(orderInfo);
        return "下单成功";
    }

    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") String id){
        log.info("reduce stock success");
        return "订单取消";
    }
}
