package com.cuisf.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    //和配置文件进行关联
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){

        return new DruidDataSource();
    }

    //后台监控
    @Bean
    public ServletRegistrationBean statViewServlet(){

        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");

        Map<String, String> initParameters  = new HashMap<>();

        //后台需要有人进行登录   账号密码设置
        initParameters.put("loginUsername","admin");  //登录的key是固定的
        initParameters.put("loginPassword","123456"); // 密码的key是固定的

        //允许谁可以访问
        initParameters.put("allow","cuisf");
        bean.setInitParameters(initParameters);
        return bean;
    }


    //filter
    @Bean
    public FilterRegistrationBean webStatViewServlet(){
        FilterRegistrationBean bean = new FilterRegistrationBean<>();

        bean.setFilter(new WebStatFilter());

        Map<String, String> initParameters  = new HashMap<>();

        //不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;

    }


}
