package com.tuling.cloud.fallback;

import com.tuling.cloud.model.response.JsonResult;
import com.tuling.cloud.feignclient.OrderServiceClient;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFeignFallback implements OrderServiceClient {
    public JsonResult getOrderById(String orderId) {
        return JsonResult.error("服务暂不可用，请稍后重试");
    }
}
