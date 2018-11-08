package com.sdx.yundian.controller.admin;

import com.sdx.yundian.controller.BaseController;
import com.sdx.yundian.entity.Gadmin;
import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.service.GadminService;
import com.sdx.yundian.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "admin/role")
public class RoleController extends BaseController {

    @Autowired
    private GadminService gadminService;
    @Autowired
    private MenuService menuService;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        String keywords = request.getParameter("keywords");
        Sort sort = new Sort(Sort.Direction.DESC, "gid");
        Page <Gadmin> roles = gadminService.findAllByLikeName("gname", keywords, page, 10, sort);
        model.addAttribute("list", roles);
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "admin/role-list";
    }

    @GetMapping("/add")
    public String roleAdd() {
        return "admin/role-add";
    }

    @GetMapping("/edit/{gadmin}")
    public String roleEdit(Model model, Gadmin gadmin) {
        model.addAttribute("gadmin", gadmin);
        return "admin/role-edit";
    }

    @GetMapping("/set-permission/{gadmin}")
    public String setPermission(Gadmin gadmin, Model model) {
        List <Menu> menus = menuService.getRecursionList(menuService.findAll());
        model.addAttribute("permission", gadmin.getMenus());
        System.out.println(menus);
        model.addAttribute("menus", menus);
        model.addAttribute("gadmin", gadmin);
        return "admin/set-permission";
    }

    @PostMapping("set-permission/{gadmin}")
    @ResponseBody
    public String setPermissionAct(Gadmin gadmin, Model model, HttpServletRequest request) {
        Enumeration em = request.getParameterNames();

        Set <Menu> menuSet = new HashSet <Menu>();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            if (name.contains("action_")) {
                String[] values = request.getParameterValues(name);
                String[] s_temp = name.split("_");
                String menu_id = s_temp[1].replace("[]", "");
                menuSet.add(new Menu(Integer.parseInt(menu_id)));
                for (int i = 0; i < values.length; i++) {
                    menuSet.add(new Menu(Integer.parseInt(values[i])));
                }
            }
        }
        gadmin.setMenus(menuSet);
        gadminService.update(gadmin);
        this.adminLog("设置权限-[" + gadmin.getGname() + "]");
        return this.outPutData("权限修改成功");
    }

    @DeleteMapping("/del/{gadmin}")
    @ResponseBody
    public String roleDel(Gadmin gadmin) {
        this.adminLog("删除角色[" + gadmin.getGname() + "]");
        gadminService.delete(gadmin);
        return this.outPutData("删除成功");
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@Valid Gadmin gadmin, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {//验证
            List <ObjectError> error = result.getAllErrors();
            for (ObjectError e : error) {
                return this.outPutErr(e.getDefaultMessage());
            }
            return null;
        } else {//验证通过
            if (gadmin.getGid() == null) {//新增

                Gadmin gadmin_new = gadminService.save(gadmin);
                if (gadmin_new != null) {
                    this.adminLog("添加角色[" + gadmin.getGname() + "]");
                    return this.outPutData("保存成功");
                }
            } else {//更新
                Gadmin gadmin_temp = gadminService.get(gadmin.getGid());
                gadmin.setMenus(gadmin_temp.getMenus());
                Gadmin gadmin_new = gadminService.update(gadmin);
                if (gadmin_new != null) {
                    this.adminLog("修改角色[" + gadmin.getGname() + "]");
                    return this.outPutData("保存成功");
                }
            }
            return this.outPutErr("保存失败,请重试");
        }
    }

}
