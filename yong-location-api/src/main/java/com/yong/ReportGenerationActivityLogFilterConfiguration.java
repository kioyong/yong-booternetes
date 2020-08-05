package com.yong;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class ReportGenerationActivityLogFilterConfiguration {


    @Bean
    public FilterRegistrationBean logFilterRegistration() {
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(getFilter());
        filterBean.setDispatcherTypes(DispatcherType.REQUEST);
        filterBean.setName("logFilter");
        filterBean.setOrder(Integer.MAX_VALUE - 1);
        return filterBean;
    }

    @Bean
    public ReportGenerationActivityLogFilter getFilter() {
        return new ReportGenerationActivityLogFilter();
    }

}
