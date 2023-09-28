package com.tuling.cloud.gateway.controller;

import com.tuling.cloud.model.request.AlarmMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.StringJoiner;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class SWAlarmController {

    @Autowired
    private MailSender sender;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.to}")
    private String to;


    /**
     * 接收 SkyWalking 的报警信息, 并做相关的业务处理(发送邮件，短信，微信等)
     * 请求必须时 POST
     */
    @PostMapping("/receive")
    public void receiveAlarm(@RequestBody List<AlarmMessage> alarmList) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者邮箱
        message.setFrom(from);
        // 接收者邮箱
        message.setTo(to);
        // 主题
        message.setSubject("告警邮件");
        // 邮件内容
        String content = getContent(alarmList);
        message.setText(content);
        sender.send(message);
        log.info("告警邮件已发送");
    }

    private String getContent(List<AlarmMessage> alarmList) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        alarmList.forEach( e -> {
            stringJoiner.add(String.format("%s: %s", "scopeId", e.getScopeId()));
            stringJoiner.add(String.format("%s: %s", "scope", e.getScope()));
            stringJoiner.add(String.format("%s: %s", "目标Scope实体的Name", e.getName()));
            stringJoiner.add(String.format("%s: %s", "目标Scope实体的Id", e.getId0()));
            stringJoiner.add(String.format("%s: %s", "Id1", e.getId1()));
            stringJoiner.add(String.format("%s: %s", "告警规则名称", e.getRuleName()));
            stringJoiner.add(String.format("%s: %s", "告警消息内容", e.getAlarmMessage()));
            stringJoiner.add(String.format("%s: %s", "告警时间", e.getStartTime()));
            stringJoiner.add(String.format("%s: %s", "标签", e.getTags()));
            stringJoiner.add("-----------------------------------------------------");
        });
       return stringJoiner.toString();
    }

}
