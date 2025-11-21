package com.jsdc.gsgwxb.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.jsdc.gsgwxb.utils.ResultInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: SatokenConfig
 * Description:
 * date: 2024/4/19 11:14
 *
 * @author bn
 */
@Configuration
public class SatokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaInterceptor((handler) -> {
            // 登录match 规则
            SaRouter.match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/app/login.do")        // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());  // 要执行的校验动作，可以写完整的 lambda 表达式

        })).addPathPatterns("/**").excludePathPatterns("/**/**", "/minio/**", "/job/editWork.do", "/job/caseData.do", "/job/testEmsNo.do", "/job/testTrajectory.do", "/networkNode/**");
    }

    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 拦截与排除 path
                .addInclude("/**").addExclude("/favicon.ico")
                // 全局认证函数
                .setAuth(obj -> {
                    // ...
                })
                // 异常处理函数
                .setError(e -> {
                    return ResultInfo.error(e.getMessage());
                })
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                            .back();
                })
                ;
    }

    // 获取配置Bean (以代码的方式配置Sa-Token, 此配置会覆盖yml中的配置)
    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("jsdcToken");             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 24 * 60 * 60);       // token有效期，单位s 默认30天
//        config.setTimeout(-1);
//        config.setActivityTimeout(-1);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setTokenStyle("uuid");               // token风格
        config.setIsReadHead(true);                 // 是否尝试从header里读取token
        config.setIsReadCookie(false);              // 是否尝试从cookie里读取token
        config.setIsLog(true);                     // 是否输出操作日志
        config.setAutoRenew(true);                  // 自动续签，指定时间内有操作，则会自动续签
        return config;
    }
}
