package com.sdx.yundian.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.controller.BaseController;
import com.sdx.yundian.entity.AdminLog;
import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.entity.Systems;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.AdminLogService;
import com.sdx.yundian.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping(value = "admin")
public class MainController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AdminLogService adminLogService;

    @GetMapping("")
    public String index(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("admin");

        List <Menu> adminMenu = (List <Menu>) redisTemplate.opsForValue().get("menu_cache_" + user.getAdminId());
        Systems system = systemService.get(1);
        model.addAttribute("sys", system);

        model.addAttribute("menus", adminMenu);
        return "admin/index";
    }

    @GetMapping("/zhuye")
    public String Zhuye(Model model) {
        Systems system = systemService.get(1);
        model.addAttribute("sys", system);
        return "admin/zhuye";
    }

    @GetMapping("/admin-log")
    public String adminLogList(Model model, HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        String keywords = request.getParameter("keywords");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page <AdminLog> adminLogs = adminLogService.findAllByLikeName("content", keywords, page, 10, sort);
        model.addAttribute("list", adminLogs);
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "admin/admin-log";
    }

    //系统设置
    @GetMapping("/system")
    public String system(Model model) {
        Systems system = systemService.get(1);
        model.addAttribute("sys", system);
        return "admin/system";
    }

    //系统设置保存
    @PostMapping("/system-save")
    @ResponseBody
    public String systemAct(Systems systems, HttpServletRequest request) {
        //文件上传
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        System.out.println(JSON.toJSONString("文件：" + file));
        Systems system = systemService.get(1);
        if (file == null) {//判断是否存在文件
            systems.setLogo(system.getLogo());
        } else {
            File logo = new File(filePath + system.getLogo());
            System.out.println(filePath + system.getLogo());
            if (logo.exists() && logo.isFile()) {
                logo.delete();
            }
            systems.setLogo(this.uploadFile(file));
        }
        if (systemService.update(systems) != null) {
            return this.outPutData("保存成功");
        }
        return this.outPutErr("保存失败,请重试");
    }

}
