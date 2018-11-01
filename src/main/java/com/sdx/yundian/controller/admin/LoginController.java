package com.sdx.yundian.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.entity.AdminLog;
import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.entity.Systems;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.MenuService;
import com.sdx.yundian.service.SystemService;
import com.sdx.yundian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "admin")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MenuService menuService;
    @Autowired
    private SystemService systemService;


    @GetMapping("login")
    public String login(Model model) {
        Systems system = systemService.get(1);//系统设置
        model.addAttribute("sys",system);
        return "admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginAt(HttpServletRequest request){
        String userName = request.getParameter("user_name");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        User admin =  userService.adminLogin(userName,password);

        if(admin == null){
            return this.outPutErr("用户名或密码不正确");
        }
        //记住密码 session 永远不失效
        if(remember =="-1"){
            request.getSession().setMaxInactiveInterval(Integer.parseInt(remember));
        }
        //缓存到redis后台有权限的菜单
        if (admin.getAdminIsSuper()) {
            List<Menu> adminMenu = menuService.getRecursionList(menuService.findAll());
            redisTemplate.opsForValue().set("menu_cache_"+admin.getAdminId(),adminMenu);
        }else{
            //不是超级管理员 查找权限
            List<Menu> adminMenu = menuService.getRecursionList(menuService.getUserMenus(admin));
            System.out.println(JSON.toJSONString(adminMenu));
            redisTemplate.opsForValue().set("menu_cache_"+admin.getAdminId(),adminMenu);
        }
        request.getSession().setAttribute("admin",admin);
        this.adminLog("登录操作");//日志记录
        return this.outPutData("登录成功");
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/admin";
    }


}
