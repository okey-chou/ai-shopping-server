package com.okeychou.aishop.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置:注册分页插件。
 *
 * 为什么必须配?
 * - 不配:selectPage(Page, Wrapper) 会执行【无 LIMIT 的全表 SQL】,然后 total 永远为 0
 * - 配了:PaginationInnerInterceptor 会在 SQL 执行前改写为 LIMIT + 自动执行 COUNT(*)
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
