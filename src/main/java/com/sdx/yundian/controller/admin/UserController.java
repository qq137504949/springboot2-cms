package com.sdx.yundian.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.controller.BaseController;
import com.sdx.yundian.entity.Gadmin;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.GadminService;
import com.sdx.yundian.service.UserService;
import com.sdx.yundian.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private GadminService gadminService;
    @GetMapping("")
    public String index(Model model,@RequestParam(value = "keywords",defaultValue = "") String keywords ,@RequestParam(value = "page",defaultValue = "1")Integer page){

        Sort sort = new Sort(Sort.Direction.DESC,"adminId");
        Page<User> datas = userService.findAllByLikeName("userName",keywords,page,10,sort);
        model.addAttribute("datas",datas);
        model.addAttribute("page",page);
        model.addAttribute("keywords",keywords);
        return "admin/user-list";
    }

    @GetMapping("/add")
    public String addUser(Model model){
        List<Gadmin> gadmins = gadminService.findAll();
        model.addAttribute("gadmins", gadmins);
        return "admin/user-add";
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveUser(@Valid User user, BindingResult result){
        if (result.hasErrors()) {//验证
            List <ObjectError> error = result.getAllErrors();
            for (ObjectError e : error) {
                return this.outPutErr(e.getDefaultMessage());
            }
            
            return null;
        }else{//验证通过

            if(user.getAdminId()!=null){
                User user_new = userService.update(user);
                if(user_new!=null){
                    this.adminLog("修改用户-"+user.getUserName());
                    return this.outPutData("保存成功");
                }
            }else{
                //新增
                user.setPassword(Tools.Md5(user.getPassword()));
                user.setCreatedAt(new Date());
                User user_new = userService.save(user);
                if(user_new!=null){
                    this.adminLog("新增用户-"+user.getUserName());
                    return this.outPutData("保存成功");
                }
            }
        }
        return this.outPutErr("保存失败,请重试");
    }

    @GetMapping("/edit/{user}")
    public String editUser(User user,Model model){
        List<Gadmin> gadmins = gadminService.findAll();
        model.addAttribute("gadmins", gadmins);
        model.addAttribute("user",user);
        return "admin/user-edit";
    }

    @DeleteMapping("/del/{user}")
    @ResponseBody
    public String delUser(User user){
        this.adminLog("删除用户-"+user.getUserName());
        userService.delete(user);
        return this.outPutData("删除成功");
    }

    @GetMapping("/set-pwd/{user}")
    public String setPwd(User user,Model model){
        model.addAttribute("user",user);
        return "admin/set-pwd";
    }

    @PostMapping("/set-pwd")
    @ResponseBody
    public String setPwdAct(HttpServletRequest request,User user){
        String oldPassword = request.getParameter("oldPassword");//旧密码
        String password = request.getParameter("password");//新密码
        User admin = userService.get(user.getAdminId());
       if(admin.getPassword().equals(Tools.Md5(oldPassword))){
           admin.setPassword(Tools.Md5(password));
           userService.update(admin);
           this.adminLog("修改用户密码-"+user.getUserName());
           return this.outPutData("修改完成");
       }else{
           return this.outPutErr("旧密码不相等");
       }
    }
}
