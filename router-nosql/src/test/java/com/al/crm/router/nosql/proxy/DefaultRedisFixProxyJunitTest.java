package com.al.crm.router.nosql.proxy;

import com.al.common.context.PropertyHolder;
import com.al.crm.nosql.cache.IRedisFix;
import com.al.crm.router.core.MDA;
import com.al.crm.router.core.RouteContext;
import com.al.crm.router.nosql.BaseJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class DefaultRedisFixProxyJunitTest extends BaseJunitTest {
    @Autowired(required = false)
    @Qualifier("provRedisCluster")
    private IRedisFix provCluster;

    @Test
    public  void testRedisFixProxy(){
        PropertyHolder.setProperties("uniconfig.domain","CRM2013");
        Map<String,String> map = new HashMap<String, String>();
        map.put("areaId_832","rds01");
        map.put("areaId_844","rds02");
        map.put("areaId_845","rds02");
        MDA.REGION_ROUTE_MAP.put("CRM2013",map);
        RouteContext.setRouteId("8450100");
        System.out.println(provCluster.set("aaaaa","bbbb2"));
        System.out.println(provCluster.get("aaaaa"));
    }
}