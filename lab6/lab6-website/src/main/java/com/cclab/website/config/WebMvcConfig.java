package com.cclab.website.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cclab.website.interceptor.MetricInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    MetricInterceptor metricInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(metricInterceptor).addPathPatterns("/**");
    }
}