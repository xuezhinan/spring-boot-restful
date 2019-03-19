package com.asiainfo.springbootrestful;

import com.asiainfo.springbootrestful.component.MyLoacalResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.util.Locale;

@SpringBootApplication
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
