package com.asiainfo.springbootrestful.dataSourcePeiZhi.handler;


import com.asiainfo.springbootrestful.dataSourcePeiZhi.DataSources;

/***
 *
 * 创建数据源的处理类   切换数据源
 */
public class DataSourceHandler {

    /***
     * 默认数据源
     */
    public static final String DEFAUL_DATASOURCE = DataSources.MASTER_DB;

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /***
     * 设置数据源
     */
    public static void setDB(String dbType){
        System.out.println(contextHolder.get());
        System.out.println("切换到"+dbType+"数据源");
        contextHolder.set(dbType);
    }

    /***
     * 获取数据源名
     */
    public static String getDB(){

        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDB(){
        contextHolder.remove();
    }
}
