package com.sdx.yundian;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.service.IMailService;
import com.sdx.yundian.tools.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(value=SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitTests {

    @Autowired
    private Sender sender;
    @Autowired
    private IMailService mailService;//注入发送邮件的各种实现方法

    @Test
    public void sendTest() throws Exception {
//        while(true){
//            String msg = new Date().toString();
            sender.send("苏东旭");
            Thread.sleep(1000);
            System.out.println("=============================");
//        }
    }

    @Test
    public void sendEmain(){
        try {
            mailService.sendSimpleMail("shxumi@163.com","测试邮件","这是一封普通的SpringBoot测试邮件");
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println( "邮件发送失败!!");
        }
        System.out.println( "邮件发送成功!!");

    }
}
