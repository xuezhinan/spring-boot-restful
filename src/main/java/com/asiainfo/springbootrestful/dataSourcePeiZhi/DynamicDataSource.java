package com.asiainfo.springbootrestful.dataSourcePeiZhi;

import com.asiainfo.springbootrestful.dataSourcePeiZhi.handler.DataSourceHandler;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/***
 *  定义一个动态数据源 要继承AbstractRoutingDataSource抽象类
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHandler.getDB();
    }
}
