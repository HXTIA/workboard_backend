# HXTIA Work-Board
## 阅读须知
* 所用到的测试表，在`work_board.sql`里

## 重要！！！【防止资源泄露】
* 记得自己添加配置文件！！！
* 参照`application-dev.yml.template`
* 主要配置以下内容，其余的保留`template`中的即可
```yaml
# 自定义配置项
homework-board:
  cfg:
    # 跨域相关配置
    cors-origins:
      - http://localhost:8080
      - 可继续像上面一样配置跨域

  # 文件上传路径【可以修改路径，不要修改前面的key】
  upload:
    base-path: /home/homework-board/
    upload-path: upload/
    image-path: image/
    video-path: video/

  # Mysql 数据库相关信息
  mysql-cfg:
    student: 你的mysql用户
    psd: 你的mysql密码
    db-ip: 你的mysql服务器IP
    db-name: work_board_test【数据库名字】
    port: 你的端口

  # Redis 数据库相关信息
  redis-cfg:
    host: 你的redis服务器IP
    psd: 你的redis密码
    port: 你的redis端口

  # 微信配置
  wx:
    app-id: 你的小程序appId
    secret: 你的小程序secret
    msgDataFormat: JSON
```

* 文件名为 `application- + 名字`
* 并且与 `application.yml`文件中的 active 保持一致，如：
```yaml
  profiles:
    active: dev
```

* 下面是我的配置
```text
└── resources                    # 放置资源、配置文件
       ├── application-dev.yml   # 开发环境
       ├── application-dev.yml.template
       ├── application-prod.yml  # 生产环境
       ├── application.yml       # 主配置
       ├── ehcache.xml
       └── run.hxtia.workbd.mapper
           └── SkillMapper.xml
```

### 注意点
* 定义与数据库相关的实体类 —— 在Pojo包中定义
* 若对于增删改没有特别的业务。直接继承BaseController类
* 若需要手写mapper，请按文件夹规范【文件夹与mapper保持一致，否则需要自己配置xml所在位置】
```text
# Eg：
├── java.run.hxtia.workbd
│   └── mapper
│       ├── SkillMapper.java
# 与上面SkillMapper.java 的位置保持一致

└── resources
    └── run.hxtia.workbd.mapper
        └── SkillMapper.xml
```
* 定义通用配置、工具 —— 请在Common模块定义
* 内部集成了Swagger文档，若添加了新模块 —— 请到`SwaggerCfg.java`中配置新模块
* 项目启动后：[📝接口文档访问地址](https://localhost:8080/swagger-ui/index.html)
* PS：项目 URL + /swagger-ui/index.html
```java
    // 参照文件中前两个配置
    @Bean
    public Docket skillDocket() {
        return groupDocket(
                "模块名称",
                "uri正则匹配",
                "模块标题",
                "模块描述");
    }

```

## 项目结构

```text
homework-board.src.main
├── java.run.hxtia.workbd
│   ├── common                   # 集成第三方库、工具
│   │   ├── commoncontroller       # 提供增删改查功能
│   │   ├── cache                # 集成 Ehcache
│   │   ├── config               # 一些配置类
│   │   ├── exception            # 统一异常处理
│   │   ├── filter               # 拦截器
│   │   ├── mapstruct            # POJO转换
│   │   ├── prop                 # 读取项目配置
│   │   ├── redis                # 集成 Redis
│   │   ├── shiro                # 集成 Shiro
│   │   ├── upload               # 文件上传【支持多文件编辑】
│   │   ├── util                # 工具类
│   │   └── validator            # 后端校验
│   ├── controller              # 网络接口层
│   │   ├── admin                # 管理模块
│   │   └── miniapp              # 小程序模块
│   ├── mapper                  # 持久层
│   ├── pojo                     # POJO对象
│   │   ├── dto
│   │   ├── po                   # 数据库字段类
│   │   └── vo
│   │       ├── request          # 请求
│   │       │   ├── page.base
│   │       │   └── save   
│   │       ├── response         # 响应
│   │       └── result           # 自定义返回结果
│   └── service                 # 业务层
│       ├── admin.impl           # 后台管理模块
│       └── miniapp.impl         # 小程序模块
└── resources                    # 放置资源、配置文件
```
