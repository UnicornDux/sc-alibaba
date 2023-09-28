package com.tuling.cloud.feignclient;

import com.tuling.cloud.model.response.JsonResult;
import com.tuling.cloud.fallback.OrderServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "order-service",
        path = "/order",
        fallback = OrderServiceFeignFallback.class
)
public interface OrderServiceClient {
    @GetMapping({"/getOrderById/{orderId}"})
    JsonResult getOrderById(@PathVariable("orderId") String orderId);
}
