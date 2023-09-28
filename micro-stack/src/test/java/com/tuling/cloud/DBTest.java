package com.tuling.cloud;

import com.tuling.cloud.mapper.StockMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DBTest {

    @Autowired
   StockMapper stockMapper;

    @Test
    public void testMybatis() {
       stockMapper.decreasePrice(100, 99.99F);
    }

}
