package run.hxtia.workbd.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import run.hxtia.workbd.common.shiro.TokenFilter;
import run.hxtia.workbd.common.util.Constants;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Swagger文档配置
 * 访问地址：%{项目地址}%/swagger-ui/index.html
 */
@Configuration
@EnableOpenApi
public class SwaggerCfg implements InitializingBean {

    /**
     * 拿到当前环境
     */
    @Autowired
    private Environment environment;
    /**
     * 是否生成文档
     */
    private boolean enable;

    // TODO  试图拆分
    @Bean
    public Docket adminBDocket() {
        return groupDocket(
            "【B端】_01_用户、角色、权限资源管理",
            "/admin/userManager/(users.*|roles.*|resources.*|auth.*)",
            "用户管理模块文档",
            "获取用户、角色、资源...");
    }

    @Bean
    public Docket orgBDocket() {
        return groupDocket(
            "【B端】_02_组织、数据管理",
            "/admin/organization/(colleges.*|grades.*|classes.*|courses.*)",
            "组织管理模块文档",
            "获取学院、年级、班级...");
    }

    @Bean
    public Docket notiworkdBDocket() {
        return groupDocket(
            "【B端】_03_通知、作业、课程管理",
            "/admin/notificationWork/(works.*|notification.*|courses.*)",
            "通知和作业管理模块文档",
            "获取通知、作业...");
    }

    @Bean
    public Docket userCDocket() {
        return groupDocket(
            "【C端】_01_用户管理",
            "/wx/studentManager/(student.*|grades.*|classes.*|courses.*|studentCourses.*)",
            "用户管理模块文档",
            "获取学生信息...");
    }

    @Bean
    public Docket orgCDocket() {
        return groupDocket(
            "【C端】_02_组织管理",
            "/wx/organization/(complexAll.*)",
            "组织管理模块文档",
            "获取组织...");
    }

    @Bean
    public Docket notificationWorksCDocket() {
        return groupDocket(
            "【C端】_03_通知作业管理",
            "/wx/notificationWork/(messages.*|notifyAndWork.*)",
            "通知作业管理模块文档",
            "获取作业、通知...");
    }




    // 构建分组模块
    private Docket groupDocket(String group,
                               String regex,
                               String title,
                               String description) {

        return baseDocket()
                .groupName(group)
                .apiInfo(apiInfo(title, description))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex(regex))
                .build();
    }

    // 基础的配置
    private Docket baseDocket() {
        // 每个接口都要传token
        RequestParameter token = new RequestParameterBuilder()
                .name(Constants.Web.HEADER_TOKEN)
                .description("用户登录令牌")
                .in(ParameterType.HEADER)
                .build();

        return new Docket(DocumentationType.OAS_30)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .ignoredParameterTypes(
                        HttpSession.class,
                        HttpServletRequest.class,
                        HttpServletResponse.class
                ).enable(enable);
    }

    // 文档的总配置
    private ApiInfo apiInfo(String title, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version("1.0.0")
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 配置只能在 dev、test环境使用文档
        enable = environment.acceptsProfiles(Profiles.of("dev", "test"));
    }

    // 配置全局的token
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList= new ArrayList<>();
        apiKeyList.add(new ApiKey("Token", "Token", "header"));
        return apiKeyList;
    }

    // 配置默认的安全上下文
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Token", authorizationScopes));
        return securityReferences;
    }

}
