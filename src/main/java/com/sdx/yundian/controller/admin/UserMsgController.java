package com.sdx.yundian.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.controller.BaseController;
import com.sdx.yundian.entity.UserMsg;
import com.sdx.yundian.service.UserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "admin/user-msg")
public class UserMsgController extends BaseController {

    @Autowired
    private UserMsgService userMsgService;

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "keywords", defaultValue = "") String keywords, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page <UserMsg> list = userMsgService.findAllByLikeName("name", keywords, page, 10, sort);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);

        return "admin/user-msg-list";
    }

    @GetMapping("/{userMsg}")
    public String show(UserMsg userMsg,Model model){
        model.addAttribute("userMsg",userMsg);
        return "admin/user-msg-show";
    }

    @DeleteMapping("/{userMsg}")
    @ResponseBody
    public String destroy(UserMsg userMsg){
        this.adminLog("删除留言-"+userMsg.getName());
        userMsgService.delete(userMsg);
        return this.outPutData("删除成功");
    }
}
