package com.asiainfo.springbootrestful.dataSourcePeiZhi.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.asiainfo.springbootrestful.dataSourcePeiZhi.DataSources;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/***
 * 配置多个datasource
 */
@Configuration
public class DataSourceConfig {


    @Bean(destroyMethod = "",name = DataSources.MASTER_DB)
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
    @Bean(destroyMethod = "",name = DataSources.SLAVE_DB)
    @ConfigurationProperties(prefix = "spring.datasourceslave")//配置文件关联的prefix不支持驼峰命名和蛇形命名（下划线）
    public DataSource datasourceSlave(){
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

}
