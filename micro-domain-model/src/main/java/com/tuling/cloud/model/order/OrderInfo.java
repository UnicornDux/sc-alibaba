package com.tuling.cloud.model.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderInfo {
    private Integer OrderId;

    private String descript;

    private Integer goodId;
}
