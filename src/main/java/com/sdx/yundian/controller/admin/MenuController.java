package com.sdx.yundian.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.entity.Menu;
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
import java.util.List;

@Controller
@RequestMapping(value = "admin/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        String keywords = request.getParameter("keywords");
        Sort sort = new Sort(Sort.Direction.ASC, "menuId");
        Page<Menu> menus = menuService.findAllByLikeName("menuName", keywords, page, 10, sort);
        System.out.println(JSON.toJSONString(menus));
        model.addAttribute("list", menus);
        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);
        return "admin/menu-list";
    }

    @GetMapping("/add")
    public String add(Model model){
        List<Menu> menus = menuService.getParentId(0);
        model.addAttribute("menus",menus);
        return "admin/menu-add";
    }

    @GetMapping("/edit/{menu}")
    public String edit(Model model,Menu menu){

        List<Menu> menus = menuService.getParentId(0);
        model.addAttribute("menus",menus);
        model.addAttribute("menu",menu);
        return "admin/menu-edit";
    }

    @DeleteMapping("destroy/{menu}")
    @ResponseBody
    public String destroy(Menu menu){
        this.adminLog("删除菜单-"+menu.getMenuName());
        menuService.delete(menu);
        return this.outPutData("删除成功");

    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@Valid Menu menu, BindingResult result){
        if(result.hasErrors()){
            List <ObjectError> error = result.getAllErrors();
            for (ObjectError e : error) {
                return this.outPutErr(e.getDefaultMessage());
            }
        }else{
            if(menu.getMenuId() == null){//新增
                if( menuService.save(menu) != null){
                    this.adminLog("添加菜单["+menu.getMenuName()+"]");
                    return this.outPutData("保存成功");
                }
            }else{//更新
                if(menuService.update(menu)!= null){
                    this.adminLog("修改菜单["+menu.getMenuName()+"]");
                    return this.outPutData("保存成功");
                }
            }

        }
        return this.outPutErr("保存失败,请重试");
    }
}
