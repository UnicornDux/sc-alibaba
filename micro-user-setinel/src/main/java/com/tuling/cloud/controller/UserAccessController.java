package com.tuling.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tuling.cloud.model.response.JsonResult;
import com.tuling.cloud.model.user.User;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/user"})
public class UserAccessController {

    @GetMapping({"/findById/{id}"})
    public User findById(@PathVariable Long id) {
        return new User(id, "张三", "123456");
    }

    @GetMapping({"/flow"})
    @SentinelResource(
            value = "flow",
            blockHandler = "handleFlowException",
            fallback = "handleFlowFallback"
    )
    public JsonResult flow() {
        return JsonResult.ok("正常访问", "flow");
    }

    public JsonResult handleFlowException(BlockException ex) {
        return JsonResult.error("服务不可用");
    }

    @PostMapping({"/addUser"})
    public JsonResult addUser(@RequestBody User user) {
        return JsonResult.ok("添加用户成功", user);
    }

    @GetMapping({"/findUserList"})
    public JsonResult findUserById() {
        return JsonResult.ok(
                "查询用户成功",
                Arrays.asList(
                        new User(108384329L, "张三", "123456"),
                        new User(123424542L, "李四", "123456"),
                        new User(153464603L, "王五", "123456")
                )
        );
    }

    @GetMapping({"/flowThread"})
    public JsonResult flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2L);
        log.info("正常访问");
        return JsonResult.error("正常访问");
    }

    @GetMapping({"/flowError"})
    public JsonResult flowError() {
        int a = Integer.parseInt("abc");
        log.info("正常访问");
        return JsonResult.error("正常访问");
    }

    @GetMapping({"/get/{id}"})
    @SentinelResource(
            value = "getById",
            blockHandler = "HotBlockHandler"
    )
    public String getById(@PathVariable("id") String id) {
        log.info("正常访问");
        return "正常访问" + id;
    }

    public String HotBlockHandler(@PathVariable("id") String id, BlockException ex) {
        return "热点限流了";
    }
}
