package com.stephen.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

/**
 * @author jack
 * @descriptionb 聊天室的启动类
 * @date 2020/5/16 11:22
 */
@Controller
@SpringBootApplication
public class ChatRoomApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatRoomApplication.class,args);
    }

}
