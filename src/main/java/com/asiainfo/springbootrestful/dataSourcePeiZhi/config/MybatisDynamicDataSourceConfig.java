package com.asiainfo.springbootrestful.dataSourcePeiZhi.config;

import com.asiainfo.springbootrestful.dataSourcePeiZhi.DataSources;
import com.asiainfo.springbootrestful.dataSourcePeiZhi.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/***
 *
 * mybatis整合动态数据源
 */
@Configuration
public class MybatisDynamicDataSourceConfig {

    @Autowired
    @Qualifier(DataSources.MASTER_DB)
    private DataSource masterDB;
    @Autowired
    @Qualifier(DataSources.SLAVE_DB)
    private DataSource slaveDB;

    /**
     *
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDB);

        //配置多数据源
        Map<Object,Object> dsMap = new HashMap<>();
        dsMap.put(DataSources.MASTER_DB,masterDB);
        dsMap.put(DataSources.SLAVE_DB,slaveDB);
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        //
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
}
