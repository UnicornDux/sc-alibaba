package com.tuling.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TBGoods {
    private Integer gid;
    private String gname;
    private Float gprice;
    private String gimage;
}
