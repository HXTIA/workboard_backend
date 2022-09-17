# HXTIA Work-Board
## 阅读须知
* 所用到的测试表，在`test.sql`里

### 注意点
* 定义与数据库相关的实体类 —— 在Pojo包中定义
* 若对于增删改没有特别的业务。直接继承BaseController类
* 若需要手写mapper，请按文件夹规范【文件夹与mapper保持一致，否则需要自己配置xml所在位置】
```text
# Eg：
├── java.run.hxtia.pubwork
│   └── mappers
│       ├── SkillMapper.java
# 与上面SkillMapper.java 的位置保持一致

└── resources
    └── run.hxtia.pubwork.mappers
        └── SkillMapper.xml
```
* 定义通用配置、工具 —— 请在Common模块定义
* 内部集成了Swagger文档，若添加了新模块 —— 请到`SwaggerCfg.java`中配置新模块
```java
    // 参照前两个配置
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

```
