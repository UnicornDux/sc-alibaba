package com.tuling.sentinel.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String name;
    private String password;
}
