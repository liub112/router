package com.al.crm.router.nosql;

import com.al.crm.nosql.cache.IRedisFix;
import com.al.crm.router.core.ClientRouter;
import com.al.crm.router.core.proxy.RouteInvocationHanlder;

public class CacheRouteInvocationHanlder extends RouteInvocationHanlder<IRedisFix>{

    public CacheRouteInvocationHanlder(ClientRouter<IRedisFix> router) {
        super(router);
    }
}
