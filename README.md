### spring-boot2-CMS
    spring-boot2+orm[hibernate+jpa]+模板thymeleaf +缓存redis 
    2.0 新增邮件发送 新增rabbitMQ 队列 web主页提交信息自动邮件发送邮件给管理员 由于发送邮件的问题使用了异步mq
### 功能模块
    图片上传 权限管理 用户管理 系统设置 日志记录等
    项目启动 自动建表 自动建库 自动填充基础数据
    默认用户名admin  密码  admin
    地址:http://域名/admin
    数据库配置
    application.properties
```# 数据库访问配置
# 主数据源，默认的
spring.datasource.url=jdbc:mysql://localhost:3306/java_demo?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false
spring.datasource.username=数据账号
spring.datasource.password=数据库密码
需要启动redis 项目才可以正常运行
#redis
spring.redis.database=0
spring.redis.host=localhost
spring.redis.port=6379
```

```
新增rabbitMQ   需要先安装 如果不需要直接注释掉 
#reabbitmq 队列
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
spring.rabbitmq.publisher-confirms=true
spring.rabbitmq.publisher-returns=true
spring.rabbitmq.template.mandatory=true
#最小消息监听线程数
spring.rabbitmq.listener.concurrency=2
#最大消息监听线程数
spring.rabbitmq.listener.max-concurrency=2

```

##邮件配置
```
# 发送邮件服务器
spring.mail.host=smtp.qq.com
#QQ邮箱
spring.mail.username=137504949@qq.com
#客户端授权码
spring.mail.password=???
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
#端口号465或587
spring.mail.properties.mail.smtp.port=465
#可以任意
spring.mail.properties.mail.display.sendmail=旭宓科技
#可以任意
spring.mail.properties.mail.display.sendname=上海旭宓信息科技有限公司
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.default-encoding=utf-8
spring.mail.from=137504949@qq.com
```
