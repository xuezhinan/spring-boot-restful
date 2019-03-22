package com.asiainfo.springbootrestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
@EnableCaching  //开启缓存的注解
public class SpringBootRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestfulApplication.class, args);
    }


    @Bean
    public ViewResolver myRe(){
        return new MyResolver();
    }

    private   static class MyResolver implements ViewResolver {

        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

    /*@Bean
    public LocaleResolver myLoacalResolver(){
        return new MyLoacalResolver();
    }*/
}
