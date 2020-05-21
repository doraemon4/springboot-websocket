package com.stephen.learning.model;

import lombok.Data;

/**
 * @author jack
 * @description 消息实体
 * @date 2020/5/16 11:24
 */
@Data
public class MessageVO {
    private String from; //发送者
    private String to;   //接收者
    private String message;
    private Integer userNum;
    private Integer type; //1:上线，2：下线，3：发送消息
}
