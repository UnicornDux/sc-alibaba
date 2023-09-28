package com.tuling.sentinel.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JsonResult {

    private Integer code;

    public String msg;

    public Object data;

    private JsonResult(Integer code, String msg, Object data) {
       this.code = code;
       this.msg = msg;
       this.data = data;
    }

    private JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static JsonResult error(String msg) {
        JsonResult jsonResult = new JsonResult(500, msg);
        return jsonResult;
    }

    public static JsonResult ok(String msg, Object data) {
        JsonResult jsonResult = new JsonResult(200, msg, data);
        return jsonResult;
    }
}
