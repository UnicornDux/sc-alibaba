package com.tuling.clond.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orderClient")
public class Orderclient {

   @Autowired
   RestTemplate restTemplate;

   @GetMapping("/add")
   public String addOrder(){
      return restTemplate.getForObject("http://order-service/order/add", String.class);
   }

}

