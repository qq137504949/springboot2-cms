package com.sdx.yundian.controller.home;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.controller.BaseController;
import com.sdx.yundian.dao.UserMsgRepository;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.entity.UserMsg;
import com.sdx.yundian.service.UserService;
import com.sdx.yundian.tools.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private Sender sender;
    @GetMapping("/")
    public String index(){
        List<User> users = userService.findAll();
        return "home/index";
    }

    @GetMapping("msg-commit")
    @ResponseBody
    public String msgCommit(UserMsg userMsg){
        sender.sendEmail(userMsg);
        return this.outPutData("success");
    }

}
