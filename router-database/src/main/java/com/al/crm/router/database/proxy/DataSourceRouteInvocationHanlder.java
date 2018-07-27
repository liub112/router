package com.al.crm.router.database.proxy;

import com.al.crm.router.core.ClientRouter;
import com.al.crm.router.core.proxy.RouteInvocationHanlder;

import javax.sql.DataSource;

public class DataSourceRouteInvocationHanlder extends RouteInvocationHanlder<DataSource>{
    public DataSourceRouteInvocationHanlder(ClientRouter<DataSource> router) {
        super(router);
    }
}
