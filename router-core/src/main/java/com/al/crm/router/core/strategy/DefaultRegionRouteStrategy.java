package com.al.crm.router.core.strategy;


import com.al.common.context.PropertyHolder;
import com.al.crm.router.core.MDA;
import com.al.crm.router.core.RouteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认路由策略
 */
public class DefaultRegionRouteStrategy implements IRouteStrategy {
    private static Logger LOG = LoggerFactory.getLogger(DefaultRegionRouteStrategy.class);

    public String determineCurrentRouteName() {
        String routeId = RouteContext.getRouteId();
        Assert.notNull(routeId, "入参未发现路由标识");

        //获取本地网级分库，如果本地网级没有则
        String key = RouteContext.CONST_ROUTE_KEY_PREFIX+"_"+routeId.substring(0, 5);
        if(resolveCacheRouteKey(key)==null){
            key = RouteContext.CONST_ROUTE_KEY_PREFIX+"_"+routeId.substring(0, 3);
        }

        String value = resolveCacheRouteKey(key);
        if(value == null){
            throw new IllegalArgumentException("未匹配分省redis路由异常");
        }
        LOG.debug("get redis instance sucess:{}",value);
        return value;
    }

    private String resolveCacheRouteKey(String key) {
        String value = null;
        String domain = PropertyHolder.getProperties("uniconfig.domain");
        //获取系统对应的地区路由映射关系表。
        Map<String, String> ref = MDA.REGION_ROUTE_MAP.get(domain);
        if(ref== null)
            throw new IllegalArgumentException("未匹配到系统对应cache路由!");

        if(ref.containsKey(key))
            value = ref.get(key);

        return value;
    }
}
