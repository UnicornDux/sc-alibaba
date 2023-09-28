package com.tuling.cloud.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface StockMapper {

    @Update("update tb_goods set gprice = #{gprice} where gid = #{gid}")
    void decreasePrice(@Param("gid") Integer productId, @Param("gprice") Float price);
}
