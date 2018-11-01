package com.sdx.yundian;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.dao.GadminRepository;
import com.sdx.yundian.entity.Gadmin;
import com.sdx.yundian.entity.Menu;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.GadminService;
import com.sdx.yundian.service.MenuService;
import com.sdx.yundian.service.UserService;
import com.sdx.yundian.tools.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YundianApplicationTests {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private GadminService gadminService;

    @Autowired
    private GadminRepository gadminRepository;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void contextLoads() {
//        User user = new User();
//        List<User> list = userRepository.findAll();
//        userRepository.findAll();
//        userRepository.findById(1);
//        userRepository.findOne(1);
//        userRepository.save(user);
//        userRepository.delete(user);
//        userRepository.count();
//        userRepository.exists(1l);
//        System.out.println(JSON.toJSON(list));

    }

    //菜单测试
    @Test
    public void menusTest(){
        List<Menu> listMenu  = menuService.findAll();
        List<Menu>listMenu_new = menuService.getRecursionList(listMenu);
        redisTemplate.opsForValue().set("test","sudongxu");
        System.out.println(JSON.toJSONString(redisTemplate.opsForValue().get("test")));
    }


    //测试登录
    @Test
    public void admin(){
        User user = userService.adminLogin("sudongxu","sudongxu");
        //System.out.println(JSON.toJSON(user));
    }
    //测试分页
    @Test
    public void pageList(){
        Pageable pageable = PageRequest.of(0,2);
        Page<User> datas = userService.findPage(pageable);
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        System.out.println(JSON.toJSON(datas));
        for(User u : datas) {
            System.out.println(u.getAdminId()+"===="+u.getUserName());
        }
//        {
//            "first": false,
//                "totalElements": 6,
//                "number": 2,
//                "last": true,
//                "numberOfElements": 2,
//                "size": 2,
//                "totalPages": 3,
//                "pageable": {
//            "paged": true,
//                    "pageNumber": 2,
//                    "offset": 4,
//                    "pageSize": 2,
//                    "unpaged": false,
//                    "sort": {
//                "unsorted": true,
//                        "sorted": false
//            }
//        },
//            "sort": {
//            "unsorted": true,
//                    "sorted": false
//        },
//            "content": [{
//            "adminLoginNum": 0,
//                    "password": "sudongxu3",
//                    "adminId": 10,
//                    "adminIsSuper": false,
//                    "userName": "sudongxu4",
//                    "email": "zhegge@qq.com",
//                    "adminGid": 0
//        }, {
//            "adminLoginNum": 0,
//                    "createdAt": 1527597680000,
//                    "password": "sudongxu4",
//                    "adminId": 12,
//                    "adminIsSuper": false,
//                    "userName": "sudongxu5",
//                    "email": "zhdf12e@qq.com",
//                    "adminGid": 0,
//                    "updatedAt": 1527597680000
//        }],
//
//        }

    }


    //计算页码的方法
    @Test
    public void PageMa(){
        //假设的页码
        int[] pags = new int[5];
        //被点击的页码
        int index = 6;
        //总页数
        int pagCount = 7;
        for (int i = 0; i < 5; i++)
        {
            //判读是否要变动页码，如果不大于3就为初始页码
            if (index > 3)
            {
                pags[i] = index - 2 + i;
                //超出最大页码，直接从后往前赋值
                if (pags[i] > pagCount) {
                    int reduce = 4;
                    for (int j = 0; j < 5; j++)
                    {
                        pags[j] = pagCount - reduce;
                        reduce--;
                    }
                }
            }
            else
            {
                pags[i] = i + 1;
            }

        }

        System.out.println(JSON.toJSON(pags));
    }

    @Test
    public void TestGadmin(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Gadmin> u = gadminService.findAllByLikeName("gname","",1,10,new Sort(Sort.Direction.DESC,"gid"));

      // Page<Gadmin> g =  gadminService.findPageLike(new Gadmin(),pageable);
//       System.out.println(JSON.toJSONString(u));
        for (Gadmin g:u.getContent()){
            System.out.println(g.getGname());
        }
    }

    @Test
    public void TestAdmin(){
        User user = new User();
        user.setUserName("admin");
        user.setPassword(Tools.Md5("sudongxu"));

        userService.save(user);
    }

    @Test
    public void TestMenu(){
        Menu menu = new Menu();

        menu.setMenuName("系统管理");
        menu.setMenuIcon("fa-bar-chart-o");
        menuService.save(menu);

    }

}
