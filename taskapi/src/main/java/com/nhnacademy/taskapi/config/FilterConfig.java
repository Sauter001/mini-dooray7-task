package com.nhnacademy.taskapi.config;

import com.nhnacademy.taskapi.filter.AccountCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AccountCheckFilter> loggingFilter(AccountCheckFilter filter) {
        FilterRegistrationBean<AccountCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
