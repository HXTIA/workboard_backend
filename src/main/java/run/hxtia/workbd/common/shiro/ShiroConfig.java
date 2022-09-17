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

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public Realm realm() {
        return new TokenRealm(new TokenMatcher());
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(Realm realm, WorkBoardProperties properties) {
        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
        // 安全管理器【并且告诉使用上面realm】
        filterBean.setSecurityManager(new DefaultWebSecurityManager(realm));
        log.debug("shiroFilterFactoryBean：Realm - {} PubWorkProperties - {}", realm, properties);
        // 添加自定义 Filter
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("token", new TokenFilter());
        filterBean.setFilters(filterMap);

        // 添加 URI 映射
        Map<String, String> uriMap = new LinkedHashMap<>();

        // 放行登录接口
        uriMap.put("/users/login", "anon");

        // 放行Swagger文档
        uriMap.put("/swagger*/**", "anon");
        uriMap.put("/v3/api-docs/**", "anon");
        uriMap.put("/wx/users/**", "anon");

        // 放行获取静态资源的URI
        uriMap.put("/" + properties.getUpload().getUploadPath() + "**", "anon");

        // 放行处理Filter内部异常的请求
        uriMap.put(ErrorFilter.ERROR_URI, "anon");

        // 其他 URI 使用自定义的 filter
        uriMap.put("/**", "token");

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
