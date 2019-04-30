package com.asiainfo.springbootrestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;


/**二、快速体验缓存
 * 		步骤：
 * 			1、开启基于注解的缓存 @EnableCaching
 * 			2、标注缓存注解即可
 * 				@Cacheable
 * 				@CacheEvict
 * 				@CachePut
 * 默认使用的是ConcurrentMapCacheManager==ConcurrentMapCache；将数据保存在	ConcurrentMap<Object, Object>中
 * 开发中使用缓存中间件；redis、memcached、ehcache；
 * 三、整合redis作为缓存
 * Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。
 * 	1、安装redis：使用docker；
 * 	2、引入redis的starter
 * 	3、配置redis
 * 	4、测试缓存
 * 		原理：CacheManager===Cache 缓存组件来实际给缓存中存取数据
 *		1）、引入redis的starter，容器中保存的是 RedisCacheManager；
 *		2）、RedisCacheManager 帮我们创建 RedisCache 来作为缓存组件；RedisCache通过操作redis缓存数据的
 *		3）、默认保存数据 k-v 都是Object；利用序列化保存；如何保存为json
 *   			1、引入了redis的starter，cacheManager变为 RedisCacheManager；
 *   			2、默认创建的 RedisCacheManager 操作redis的时候使用的是 RedisTemplate<Object, Object>
 *   			3、RedisTemplate<Object, Object> 是 默认使用jdk的序列化机制
 *      4）、自定义CacheManager；
 *
 */
@EnableTransactionManagement  //开启事务控制注解
@SpringBootApplication
@EnableAsync  //开启异步线程注解
@EnableScheduling  // 开启定时任务注解
@EnableCaching  //开启缓存的注解
public class SpringBootRestfulApplication {

    public static void main(String[] args) {
        // springboot整合redis和elasticsearch需要作如下处理才能正常启动
        System.setProperty("es.set.netty.runtime.available.processors","false");
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
