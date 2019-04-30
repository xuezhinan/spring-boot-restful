package com.asiainfo.springbootrestful.dataSourcePeiZhi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 *  创建切换数据源的注解类
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
}) //规定注解只使用在方法上
public @interface RoutingDataSource {
    //默认值使用masterDB
    String value() default DataSources.MASTER_DB;
}
