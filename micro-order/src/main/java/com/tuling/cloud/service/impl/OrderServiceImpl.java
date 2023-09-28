package com.tuling.cloud.service.impl;

import com.tuling.cloud.feignclient.StockClient;
import com.tuling.cloud.mapper.OrderMapper;
import com.tuling.cloud.model.order.OrderInfo;
import com.tuling.cloud.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockClient stockClient;

    /**
     * 测试 seata 全局事务的可用性
     * @param orderInfo
     */
    @Trace
    @Override
    @GlobalTransactional
    public void create(OrderInfo orderInfo) {
        stockClient.decreasePrice(orderInfo.getGoodId(),99.0F);
        orderMapper.create(orderInfo);
        int i = 10 / 0;
    }
}
