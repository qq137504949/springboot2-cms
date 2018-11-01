package com.sdx.yundian.core;

import com.sdx.yundian.dao.MenuRepository;
import com.sdx.yundian.dao.SystemRepository;
import com.sdx.yundian.dao.UserRepository;
import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.entity.Systems;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartApplicationListener implements ApplicationListener <ContextRefreshedEvent> {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected SystemRepository systemRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("====系统初始化====");
        //用户初始化
        if (userRepository.count() <= 0) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(Tools.Md5("admin"));
            user.setAdminIsSuper(true);
            user.setEmail("137504949@qq.com");
            userRepository.save(user);
            System.out.println("==初始化账号密码完毕==");
        }

        //系统设置初始化
        if (systemRepository.count() <= 0) {
            Systems system = new Systems();
            system.setName("上海旭宓科技");
            system.setKeywords("SEO-关键字");
            system.setDescription("SEO优化的描述");
            system.setUrl("http://www.shxumi.com");
            system.setAddress("上海市闵行区");
            system.setEmail("shxumi@163.com");
            system.setMobile("18602156507");
            system.setIpc("备案号");
            system.setCompany("上海旭宓信息科技有限公司");
            systemRepository.save(system);
            System.out.println("==初始化系统设置==");
        }

        //菜单初始化
        if (menuRepository.count() <= 0) {

            Menu menu = new Menu();
            menu.setMenuName("系统管理");
            menu.setMenuIcon("fa-bar-chart-o");
            Menu menu1 = menuRepository.save(menu);

            Menu menu2 = new Menu();
            menu2.setMenuName("角色管理");
            menu2.setMenuLink("admin/role");
            menu2.setParentId(menu1.getMenuId());

            Menu menu3 = new Menu();
            menu3.setMenuName("用户管理");
            menu3.setMenuLink("admin/user");
            menu3.setParentId(menu1.getMenuId());

            Menu menu4 = new Menu();
            menu4.setMenuName("菜单管理");
            menu4.setMenuLink("admin/menu");
            menu4.setParentId(menu1.getMenuId());

            Menu menu5 = new Menu();
            menu5.setMenuName("系统设置");
            menu5.setMenuLink("admin/admin-log");
            menu5.setParentId(menu1.getMenuId());

            Menu menu6 = new Menu();
            menu6.setMenuName("管理员日志");
            menu6.setMenuLink("admin/system");
            menu6.setParentId(menu1.getMenuId());

            menuRepository.save(menu2);
            menuRepository.save(menu3);
            menuRepository.save(menu4);
            menuRepository.save(menu5);
            menuRepository.save(menu6);

            System.out.println("==初始化菜单完毕==");
        }


    }
}
