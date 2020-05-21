package com.stephen.learning.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.net.UnknownHostException;

/**
 * @author jack
 * @description
 * @date 2020/5/21 22:36
 */
@Controller
public class UserController {
    @Autowired
    MyWebSocket myWebSocket;
    /**
     * 登陆界面
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    /**
     * 聊天界面
     */
    @GetMapping("/login")
    public ModelAndView index(@NotNull String username) throws UnknownHostException {
        if (StringUtils.isEmpty(username)) {
            username = "匿名用户";
        }else {
            if(myWebSocket.sessions.containsKey(username)){
                ModelAndView modelAndView = new ModelAndView("/chat");
                modelAndView.addObject("message","该用户已经登录，将跳转登录页面！");
                return modelAndView;
            }
        }
        ModelAndView modelAndView = new ModelAndView("/chat");
        modelAndView.addObject("username", username);
        return modelAndView;
    }
}
