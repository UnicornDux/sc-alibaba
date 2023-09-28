package com.tuling.cloud.Service.impl;

import com.tuling.cloud.Service.StockService;
import com.tuling.cloud.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {


    @Autowired
    private StockMapper stockMapper;


    @Override
    public void decreasePrice(Integer productId, Float price) {

       stockMapper.decreasePrice(productId,price);
    }
}
