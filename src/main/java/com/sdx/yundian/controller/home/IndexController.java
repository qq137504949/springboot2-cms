package com.sdx.yundian.controller.home;

import com.sdx.yundian.controller.BaseController;
import com.sdx.yundian.entity.Systems;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.entity.UserMsg;
import com.sdx.yundian.service.SystemService;
import com.sdx.yundian.service.UserService;
import com.sdx.yundian.tools.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;

    @Autowired
    private Sender sender;

    @GetMapping("/")
    public String index(Model model) {
        Systems  systems = systemService.get(1);
        model.addAttribute("sys",systems);
        return "home/index";
    }

    @PostMapping("msg-commit")
    @ResponseBody
    public String msgCommit(@Valid UserMsg userMsg, BindingResult result) {
        if (result.hasErrors()) {
            List <ObjectError> error = result.getAllErrors();
            for (ObjectError e : error) {
                return this.outPutErr(e.getDefaultMessage());
            }
        }
        sender.sendEmail(userMsg);//发送邮件
        return this.outPutData("提交成功,我们将尽快和您联系");

    }

}
