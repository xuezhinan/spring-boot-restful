package com.asiainfo.springbootrestful.service;

import com.alibaba.fastjson.JSON;
import com.asiainfo.springbootrestful.dataSourcePeiZhi.DataSources;
import com.asiainfo.springbootrestful.dataSourcePeiZhi.RoutingDataSource;
import com.asiainfo.springbootrestful.entities.UserRole;
import com.asiainfo.springbootrestful.mapper.UserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class QryService {

    //创建线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    UserRoleMapper userRoleMapper;
    /**
* 将方法的运行结果进行缓存；以后再要相同的数据，直接从缓存中获取，不用调用方法；
* CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字；
*
* 原理：
*   1、自动配置类；CacheAutoConfiguration
*   2、缓存的配置类
*   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
*   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
*   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
*   3、哪个配置类默认生效：SimpleCacheConfiguration；
*
*   4、给容器中注册了一个CacheManager：ConcurrentMapCacheManager
*   5、可以获取和创建ConcurrentMapCache类型的缓存组件；他的作用将数据保存在ConcurrentMap中；
*
*   运行流程：
*   @Cacheable：
*   1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
*      （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建。
*   2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
*      key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
*          SimpleKeyGenerator生成key的默认策略；
*                  如果没有参数；key=new SimpleKey()；
*                  如果有一个参数：key=参数的值
*                  如果有多个参数：key=new SimpleKey(params)；
*   3、没有查到缓存就调用目标方法；
*   4、将目标方法返回的结果，放进缓存中
*
*   @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
*   如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
*
*   核心：
*      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
*      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
*
*
*   几个属性：
*      cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
*
*      key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值  1-方法的返回值
*              编写SpEL； #i d;参数id的值   #a0  #p0  #root.args[0]
*              getEmp[2]
*
*      keyGenerator：key的生成器；可以自己指定key的生成器的组件id
*              key/keyGenerator：二选一使用;
*
*
*      cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
*
*      condition：指定符合条件的情况下才缓存；
*              ,condition = "#id>0"
*          condition = "#a0>1"：第一个参数的值》1的时候才进行缓存
*
*      unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
*              unless = "#result == null"
*              unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
*      sync：是否使用异步模式
*/
    //@Cacheable(cacheNames = {"role"},unless = "#result == null ",keyGenerator = "myKeyGenerator")
    @RoutingDataSource(value = DataSources.SLAVE_DB)
    public List<Map<String, Object>> getRole(){
        PageHelper.startPage(1,20);
        List<Map<String, Object>> roleList = userRoleMapper.getUserRole();
        PageInfo pageInfo = new PageInfo(roleList);
        long total = pageInfo.getTotal();
        log.info("------------>"+"进入getRole的的service,总条数是："+total);
        return roleList;
    }

    @Cacheable(cacheNames = {"roleID"},keyGenerator = "myKeyGenerator")
    public UserRole getRoleById(Integer id){
        UserRole role = userRoleMapper.getRoleById(id);

        log.info("service查询结果是："+role);
        return role;
    }

    /**
     * @CachePut：既调用方法，又更新缓存数据；同步更新缓存
     * 修改了数据库的某个数据，同时更新缓存；
     * 运行时机：
     *  1、先调用目标方法
     *  2、将目标方法的结果缓存起来
     *
     * 测试步骤：
     *  1、查询1号员工；查到的结果会放在缓存中；
     *          key：1  value：lastName：张三
     *  2、以后查询还是之前的结果
     *  3、更新1号员工；【lastName:zhangsan；gender:0】
     *          将方法的返回值也放进缓存了；
     *          key：传入的employee对象  值：返回的employee对象；
     *  4、查询1号员工？
     *      应该是更新后的员工；
     *          key = "#employee.id":使用传入的参数的员工id；
     *          key = "#result.id"：使用返回后的id
     *             @Cacheable的key是不能用#result
     *      为什么是没更新前的？【1号员工没有在缓存中更新】
     *
     */
    //@CachePut(cacheNames = {"update"},keyGenerator = "myKeyGenerator")
    @RoutingDataSource(value = DataSources.MASTER_DB)
    public int insert(UserRole userRole){
        int num = userRoleMapper.insert(userRole);
        return num;
    }

    /**
     * @CacheEvict：缓存清除
     *  key：指定要清除的数据
     *  allEntries = true：指定清除这个缓存中所有的数据
     *  beforeInvocation = false：缓存的清除是否在方法之前执行
     *      默认代表缓存清除操作是在方法执行之后执行;如果出现异常缓存就不会清除
     *
     *  beforeInvocation = true：
     *      代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
     *
     *
     */
    public List<Map<String,Object>> joinget(){
        PageHelper.startPage(2,3);
        List<Map<String, Object>> mapList = userRoleMapper.joinSelect();
        return mapList;
    }

    public String testAsync() throws ExecutionException, InterruptedException, TimeoutException {
        log.info("service开始："+System.currentTimeMillis());
        Callable<String> rep1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟调用远程接口耗时
                Thread.sleep(1500);
                return "接口一返回的数据";
            }
        };
        Callable<String> rep2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟调用远程接口耗时
                Thread.sleep(1500);
                return "接口二返回的数据";
            }
        };

        FutureTask<String> rep_1_finall = new FutureTask<>(rep1);
        FutureTask<String> rep_2_finall = new FutureTask<>(rep2);
        //启动线程处理任务,此处可以用线程池优化
        /*new Thread(rep_1_finall).start();
        new Thread(rep_2_finall).start();*/
        executorService.submit(rep_1_finall);
        executorService.submit(rep_2_finall);
        Map<String , Object> result = new HashMap<String, Object>();
        result.put("res1",rep_1_finall.get());
        //设置超时时间
        //result.put("res2",rep_2_finall.get(3000,TimeUnit.MILLISECONDS));
        result.put("res2",rep_2_finall.get());
        log.info("service结束："+System.currentTimeMillis());
        return JSON.toJSONString(result);
    }
}
