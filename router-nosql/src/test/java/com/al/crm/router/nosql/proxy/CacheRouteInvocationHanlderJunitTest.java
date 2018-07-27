package com.al.crm.router.nosql.proxy;


import com.al.common.context.PropertyHolder;
import com.al.crm.nosql.cache.IRedisFix;
import com.al.crm.router.core.MDA;
import com.al.crm.router.core.RouteContext;
import com.al.crm.router.nosql.BaseJunitTest;
import com.al.crm.router.nosql.CacheRouteInvocationHanlder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CacheRouteInvocationHanlderJunitTest extends BaseJunitTest {

    @Autowired
    private CacheRouteInvocationHanlder routeInvocationHanlder;


    @Test
    public void testCacheRouteInvocationHanlder(){
        IRedisFix provCluster = (IRedisFix) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader()
                ,new Class[]{IRedisFix.class},routeInvocationHanlder);
        PropertyHolder.setProperties("uniconfig.domain","CRM2013");
        Map<String,String> map = new HashMap<String, String>();
        map.put("areaId_832","rds01");
        map.put("areaId_844","rds02");
        map.put("areaId_845","rds02");
        MDA.REGION_ROUTE_MAP.put("CRM2013",map);
        RouteContext.setRouteId("8320100");
        System.out.println(provCluster.set("aaaaa","bbbb1"));
        System.out.println(provCluster.get("aaaaa"));

        RouteContext.setRouteId("8440100");
        System.out.println(provCluster.set("aaaaa","bbbb2"));
        System.out.println(provCluster.get("aaaaa"));

        RouteContext.setRouteId("8450100");
        System.out.println(provCluster.set("aaaaa","bbbb2"));
        System.out.println(provCluster.get("aaaaa"));
    }

}