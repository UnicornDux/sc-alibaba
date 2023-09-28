package com.tuling.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.tuling.sentinel.entity.JsonResult;
import com.tuling.sentinel.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class HelloSentinelController {

    // 资源的名称需要与接口对应的访问的路径一致
    public static final String RESOURCE_NAME = "hello";
    public static final String USER_RESOURCE_NAME = "getUserInfo";
    public static final String DEGRADE_RESOURCE_NAME = "degrade";


    @RequestMapping("/hello")
    public String hello() {

        Entry entry = null;

        try {
            // sentinel 针对资源进行限制
            entry = SphU.entry(RESOURCE_NAME);
            // 被保护的业务逻辑
            String str = "hello sentinel";
            log.info(str);
            return str;
        } catch (BlockException e) {
            // 资源访问阻止， 被限流或被降级
            // 进行相应的处理操作
            log.info("资源被限流了");
            return "资源暂不可用";
        } catch (Exception ex) {
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(ex, entry);
        }finally {
            if(entry != null) {
                entry.exit();
            }
        }
        return null;
    }

    /**
     * 配置了限流规则
     */
    @PostConstruct
    public static void initFlowRules() {

        List<FlowRule> rules = new ArrayList<>();
        // 流控对象
        FlowRule rule = new FlowRule();
        // 设置资源
        rule.setResource(RESOURCE_NAME);
        // 设置流规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受访问的资源的阈值
        rule.setCount(1);
        // 添加到集合中
        rules.add(rule);
        // 加载到规则管理器中
        // FlowRuleManager.loadRules(rules);

        /* ------------------------------------------------------------------------------  */
        // 配置限流规则 USER_RESOURCE_NAME
        FlowRule rule2 = new FlowRule();
        rule2.setResource(USER_RESOURCE_NAME);
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setCount(1);
        rules.add(rule2);
        FlowRuleManager.loadRules(rules);
    }


    /**
     *  -------------------------------------------------------------------------------
     *  >  @SentinelResource 使用配置说明
     *  -------------------------------------------------------------------------------
     *  0. 引入依赖 sentinel-annotation-aspectj,
     *  1. 构建返回 SentinelResourceAspect 对象的 Bean
     *  2. value: 资源名称
     *  3. blockHandler: 限流处理器, 此函数必须建立在当前类中，如果在其他类中需要做配置
     *  4. blockHandlerClass = {User.class} 当处理函数在其他的类中的时候使用这个参数来指定
     *          <strong> 其他类中的处理方法需要申明为 static </strong>
     *  5. fallback: 降级处理器 (同上)
     *  6. fallbackClass = {User.class} 当处理函数在其他的类中的时候使用这个参数来指定
     *          <strong> 其他类中的处理方法需要申明为 static </strong>
     *     ---------------------------------------------------------------------------
     *  7.   >  当 blockHandler 与 fallbackHandler 同时发生的时候，会按照限流规则来处理
     *     ---------------------------------------------------------------------------
     *  8. exceptionsToIgnore= {} 用于说明哪些异常不做处理
     *
     * @return
     */

    @GetMapping("/user/{userId}")
    @SentinelResource(value = USER_RESOURCE_NAME, blockHandler = "userBlockHandler")
    public JsonResult getUserInfo(@PathVariable("userId") Long userId) {
        log.info("查询用户信息: 用户 ID ：{}", userId);
        User user = new User();
        user.setName("张三");
        user.setPassword("123456");
        return JsonResult.ok("获取用户信息成功", user);
    }

    /**
     * blockHandler 函数的要求
     * --------------------------------------------------------------------------------
     *  1. 方法的修饰符必须是 public
     *  2. 方法的返回值需要与原来的方法一致
     *  3. 方法参数与被处理的方法一致，可以额外添加一个BlockException参数,
     * --------------------------------------------------------------------------------
     * @param ex
     * @return
     */
    public JsonResult userBlockHandler(@PathVariable("userId") Long userId, BlockException ex) {
        ex.printStackTrace();
        log.error("资源被限流了: 查询用户的 ID：{}", userId);
        return JsonResult.error("资源被限流了");
    }


    @RequestMapping("/degrade")
    @SentinelResource(
            value = DEGRADE_RESOURCE_NAME,
            entryType = EntryType.IN,
            blockHandler = "blockHandlerForFb"
    )
    public JsonResult degrade(String id) {
        throw new RuntimeException("程序异常了");
    }

    public JsonResult blockHandlerForFb(String id, BlockException ex){
        return JsonResult.error("程序暂不可用，请稍后重试");
    }

    @PostConstruct
    public void initDegradeRule() {
        // 降级规则，异常处理
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(DEGRADE_RESOURCE_NAME);
        // 熔断规则：支持慢调用比例 / 异常比例 / 异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 设置阈值
        rule.setCount(2);
        // 统计时长(单位:ms)
        rule.setStatIntervalMs(60 * 1000);
        // 熔断的时长(单位:s) 10s 后进入半开状态，如果再次请求失败，直接熔断，
        rule.setTimeWindow(10);
        // 触发熔断的最小请求数
        rule.setMinRequestAmount(2);
        // ------------------------------------
        // 以上的配置含义： 一分钟内，调用了两次，出现了两次异常，熔断资源
        // ------------------------------------
        degradeRules.add(rule);
        DegradeRuleManager.loadRules(degradeRules);
    }



    @GetMapping("/1233")
    public JsonResult getTimeout(){
        log.info("start-Time: {}", LocalDateTime.now().toLocalTime());
        try {
         /*   synchronized (this){
                this.wait(5000);
            }*/
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("end-Time: {}", LocalDateTime.now().toLocalTime());
        return JsonResult.error("服务不可用");
    }
}
