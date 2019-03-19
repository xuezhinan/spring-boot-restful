package com.asiainfo.springbootrestful.config;

import com.asiainfo.springbootrestful.component.LoginInterceptors;
import com.asiainfo.springbootrestful.component.MyLoacalResolver;
import com.asiainfo.springbootrestful.filter.EncodingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 *
 * 配置类，可以向spring容器中注入bean
 * 这里可以实现WebMvcConfigurer接口，也可以不实现
 */
//@EnableWebMvc 这个注解表示全面接管MVC的配置，有此注解springboot的默认mvc配置等等就不会生效
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //添加默认映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("main.html").setViewName("dashboard");
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptors()).addPathPatterns("/**")
                .excludePathPatterns("/","/index.html","/login","/webjars/**","/asserts/**");
        //剔除掉css和js文件的请求，不然也会被拦截器拦截掉，没有样式。
    }

    //配置bean可以单独写在配置类里
    @Bean
    //注意这里的方法名称必须是localeResolver，方法的返回类型是注入的bean的类型，方法名是bean的id
    public LocaleResolver localeResolver(){
        System.out.println("--------------------》添加了组件MyLoacalResolver");
        return new MyLoacalResolver();
    }

    //添加过滤器，相当于在web.xml文件中配置filter
    @Bean
    public FilterRegistrationBean registFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new EncodingFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("EncodeFilter");
        //如果有多个过滤器，用来设置过滤器的调用顺序，也可以在过滤器类上配置注解@Order,
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
