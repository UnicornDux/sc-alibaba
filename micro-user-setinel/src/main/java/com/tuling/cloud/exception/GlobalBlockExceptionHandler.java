package com.tuling.cloud.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuling.cloud.model.response.JsonResult;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalBlockExceptionHandler implements BlockExceptionHandler {

    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.info("BlockExceptionHandler BlockException============>>:{}", e.getRule());
        JsonResult r = null;
        if (e instanceof FlowException) {
            r = JsonResult.error(505, "接口限流");
        } else if (e instanceof DegradeException) {
            r = JsonResult.error(506, "接口降级");
        } else if (e instanceof ParamFlowException) {
            r = JsonResult.error(507, "接口参数限流");
        } else if (e instanceof SystemBlockException) {
            r = JsonResult.error(508, "系统限流");
        } else if (e instanceof AuthorityException) {
            r = JsonResult.error(509, "无权访问");
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        httpServletResponse.setContentType("application/json");
        (new ObjectMapper()).writeValue(httpServletResponse.getWriter(), r);
    }
}
