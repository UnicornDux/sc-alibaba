package com.tuling.cloud.mapper;

import com.tuling.cloud.model.order.OrderInfo;
import org.apache.ibatis.annotations.Insert;

public interface OrderMapper {

    @Insert("insert into order_inf (descript,good_Id) values (#{descript},#{goodId})")
    public void create(OrderInfo orderInfo);
}
