package com.sdx.yundian.controller;

import com.alibaba.fastjson.JSON;
import com.sdx.yundian.entity.AdminLog;
import com.sdx.yundian.entity.Message;
import com.sdx.yundian.entity.User;
import com.sdx.yundian.service.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BaseController {

    protected final static String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploads/";

    @Autowired
    private AdminLogService adminLogService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 提示消息
     *
     * @param msg 提示文字
     * @return
     */
    public String showMsg(String msg) {
        String temp_url = "redirect:/show-msg?msg=" + msg;
        return temp_url;
    }

    /**
     * 管理员日志记录
     * @param text 操作内容
     */
    public void adminLog(String text) {
        User user = (User) request.getSession().getAttribute("admin");
        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(user.getAdminId());
        adminLog.setAdminName(user.getUserName());
        adminLog.setContent(text);
        adminLog.setCreatedAt(new Date());
        adminLog.setIp(this.getIpAddr());
        adminLogService.save(adminLog);
    }

    /**
     * 文件上传
     *
     * @param file 文件流
     * @return 上传路径
     */
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();//文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//文件后缀
        String newFileName = UUID.randomUUID().toString() + suffixName;
        System.out.println(newFileName);
        File dest = new File(filePath + getStringDate("yyyy-MM-dd") + "/" + newFileName);
        File fileParent = dest.getParentFile();
        //判断是否存在
        if (!fileParent.exists()) {
            fileParent.mkdirs();//没有这个目录直接创建目录
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/uploads/" + getStringDate("yyyy-MM-dd") + "/" + newFileName;
    }

    /**
     * 提示消息
     *
     * @param msg 提示文字
     * @param url 跳转结果
     * @return
     */
    public String showMsg(String msg, String url) {
        String temp_url = "redirect:/show-msg?msg=" + URLEncoder.encode(msg) + "&url=" + url;
        System.out.println(temp_url);
        return temp_url;
    }

    public String outPutData(Object data) {
        Message msg = new Message(data.toString(), 0);
        return JSON.toJSONString(msg);
    }

    public String outPutErr(String err) {
        Message msg = new Message(err, -1);
        return JSON.toJSONString(msg);
    }

    /**
     * @param format yyyy-MM-dd HH:mm:ss
     * @return 当前字符串时间格式
     */
    public static String getStringDate(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * @Description: 获取客户端IP地址
     */
    public String getIpAddr() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
