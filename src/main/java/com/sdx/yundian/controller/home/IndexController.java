package com.sdx.yundian.controller.home;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(){
        List<User> users = userService.findAll();
        return "home/index";
    }

}
