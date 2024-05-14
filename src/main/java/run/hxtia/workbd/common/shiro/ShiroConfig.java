package run.hxtia.workbd.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.hxtia.workbd.common.filter.ErrorFilter;
import run.hxtia.workbd.common.prop.WorkBoardProperties;
import run.hxtia.workbd.common.util.Constants;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * 将此配置文件单拎出来，和redis一样的原因
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 将自定义的Realm放入IOC容器中
     */
    @Bean
    public Realm realm() {
        return new TokenRealm(new TokenMatcher());
    }

    /**
     * Shiro过滤器工厂
     * @param realm：Shiro数据源
     * @param properties：项目配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(Realm realm, WorkBoardProperties properties) {
        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
        // 安全管理器【并且告诉使用上面realm】
        filterBean.setSecurityManager(new DefaultWebSecurityManager(realm));
        log.debug("shiroFilterFactoryBean：Realm - {} PubWorkProperties - {}", realm, properties);
        // 添加自定义 Filter
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("token", new TokenFilter());
        filterMap.put("wxToken", new WxTokenFilter());
        filterBean.setFilters(filterMap);

        // 添加 URI 映射
        Map<String, String> uriMap = new LinkedHashMap<>();

        // 放行登录&注册接口&发送验证码&忘记密码
        uriMap.put("/admin/userManager/users/login", "anon");
        uriMap.put("/admin/userManager/users/register", "anon");
        uriMap.put("/admin/userManager/users/sendEmail", "anon");
        uriMap.put("/wx/studentManager/student/getToken", "anon");
        uriMap.put("/admin/userManager/users/forgotPwd", "anon");
        uriMap.put("/admin/userManager/users/captcha", "anon");

        // test
        uriMap.put("/**", "anon");
        uriMap.put("/wx/**", "anon");


        // 放行Swagger文档
//        uriMap.put("/swagger**/**", "anon");
//        uriMap.put("/v3/api-docs/**", "anon");

        // 放行获取静态资源的URI
        uriMap.put("/" + properties.getUpload().getUploadPath() + "**", "anon");

        // 放行处理Filter内部异常的请求
        uriMap.put(Constants.Web.ERROR_URI, "anon");

        // 其他 admin URI 使用自定义的 filter token
        uriMap.put("/admin/**", "token");
        // 其他 admin URI 使用自定义的 filter wxToken
        uriMap.put("/wx/**", "wxToken");

        filterBean.setFilterChainDefinitionMap(uriMap);
        return filterBean;
    }

    /**
     * 权限控制才会用到
     * 解决：使用注解 @RequiresPermissions导致控制器接口404
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator proxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setUsePrefix(true);
        return proxyCreator;
    }

}
