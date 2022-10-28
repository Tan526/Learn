package com.example.demo.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public
    org.apache.shiro.mgt.SecurityManager securityManager(){
        return null;
    }

    /**
     * ShiroFilter是整个Shiro的入口点，用于拦截需要安全控制的请求进行处理
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilter.setSecurityManager(securityManager);

        /**
         *身份认证失败，则跳转到登录页面的配置 没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面。
         * shiroFilter.setLoginUrl("");
         * 登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此。
         * shiroFilter.setSuccessUrl("");
         * 没有权限默认跳转的页面
         * shiroFilter.setUnauthorizedUrl("");
         * filterChainDefinitions的配置顺序为自上而下,以最上面的为准
         * shiroFilter.setFilterChainDefinitions("");
         **/
        shiroFilter.setLoginUrl("/userLogin");
        shiroFilter.setUnauthorizedUrl("/");

        //自定义过滤
        //oauth2
        Map<String, Filter> filters = new LinkedHashMap();
        // filters.put("front", new FrontFilter());
        // filters.put("backend", new BackendFilter());
        // filters.put("logout", dmoLogoutFilter);
        // filters.put("anon", new AnonymousFilter());
        // filters.put("user", new UserFilter());
        // filters.put("redirect", new ShiroRedirectFilter());
        // filters.put("oauth2", new Oauth2Filter());
        shiroFilter.setFilters(filters);

        //当运行一个Web应用程序时,Shiro将会创建一些有用的默认Filter实例,并自动地在[main]项中将它们置为可用自动地可用的默认的Filter实例是被DefaultFilter枚举类定义的,枚举的名称字段就是可供配置的名称
        /**
         * anon---------------org.apache.shiro.web.filter.authc.AnonymousFilter 没有参数，表示可以匿名使用。
         * authc--------------org.apache.shiro.web.filter.authc.FormAuthenticationFilter 表示需要认证(登录)才能使用，没有参数
         * authcBasic---------org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter 没有参数表示httpBasic认证
         * logout-------------org.apache.shiro.web.filter.authc.LogoutFilter
         * noSessionCreation--org.apache.shiro.web.filter.session.NoSessionCreationFilter
         * perms--------------org.apache.shiro.web.filter.authz.PermissionAuthorizationFilter 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
         * port---------------org.apache.shiro.web.filter.authz.PortFilter port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
         * rest---------------org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter 根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
         * roles--------------org.apache.shiro.web.filter.authz.RolesAuthorizationFilter 参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
         * ssl----------------org.apache.shiro.web.filter.authz.SslFilter 没有参数，表示安全的url请求，协议为https
         * user---------------org.apache.shiro.web.filter.authz.UserFilter 没有参数表示必须存在用户，当登入操作时不做检查
         */

        /**
         * 通常可将这些过滤器分为两组
         * anon,authc,authcBasic,user是第一组认证过滤器
         * perms,port,rest,roles,ssl是第二组授权过滤器
         * 注意user和authc不同：当应用开启了rememberMe时,用户下次访问时可以是一个user,但绝不会是authc,因为authc是需要重新认证的
         * user表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe 说白了,以前的一个用户登录时开启了rememberMe,然后他关闭浏览器,下次再访问时他就是一个user,而不会authc
         *
         *
         * 举几个例子
         *  /admin=authc,roles[admin]      表示用户必需已通过认证,并拥有admin角色才可以正常发起'/admin'请求
         *  /edit=authc,perms[admin:edit]  表示用户必需已通过认证,并拥有admin:edit权限才可以正常发起'/edit'请求
         *  /home=user     表示用户不一定需要已经通过认证,只需要曾经被Shiro记住过登录状态就可以正常发起'/home'请求
         */


        /**
         * 各默认过滤器常用如下(注意URL Pattern里用到的是两颗星,这样才能实现任意层次的全匹配)
         *  /admins/**=anon             无参,表示可匿名使用,可以理解为匿名用户或游客
         *  /admins/user/**=authc       无参,表示需认证才能使用
         *  /admins/user/**=authcBasic  无参,表示httpBasic认证
         *  /admins/user/**=ssl         无参,表示安全的URL请求,协议为https
         *  /admins/user/**=perms[user:add:*]  参数可写多个,多参时必须加上引号,且参数之间用逗号分割,如/admins/user/**=perms["user:add:*,user:modify:*"]。当有多个参数时必须每个参数都通过才算通过,相当于isPermitedAll()方法
         *  /admins/user/**=port[8081] 当请求的URL端口不是8081时,跳转到schemal://serverName:8081?queryString。其中schmal是协议http或https等,serverName是你访问的Host,8081是Port端口,queryString是你访问的URL里的?后面的参数
         *  /admins/user/**=rest[user] 根据请求的方法,相当于/admins/user/**=perms[user:method],其中method为post,get,delete等
         *  /admins/user/**=roles[admin]  参数可写多个,多个时必须加上引号,且参数之间用逗号分割,如：/admins/user/**=roles["admin,guest"]。当有多个参数时必须每个参数都通过才算通过,相当于hasAllRoles()方法
         *
         */


        //Shiro验证URL时,URL匹配成功便不再继续匹配查找(所以要注意配置文件中的URL顺序,尤其在使用通配符时)
        // 配置不会被拦截的链接 顺序判断
        Map<String, String> filterMap = new LinkedHashMap();
        filterMap.put("/userLogin", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/web/**", "anon");
        filterMap.put("/login", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

}
