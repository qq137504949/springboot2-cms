# spring-boot2-CMS
spring-boot2+orm[hibernate+jpa]+模板thymeleaf +缓存redis 
功能模块
    图片上传 权限管理 用户管理 系统设置 日志记录等
MIT
    项目启动 自动建表 自动建库 自动填充基础数据
    默认用户名admin  密码  admin
    地址:http://域名/admin
    数据库配置
    application.properties
    # 数据库访问配置
# 主数据源，默认的
    spring.datasource.url=jdbc:mysql://localhost:3306/java_demo?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false
    spring.datasource.username=数据账号
    spring.datasource.password=数据库密码
    需要启动redis 项目才可以正常运行
    #redis
    spring.redis.database=0
    spring.redis.host=localhost
    spring.redis.port=6379
