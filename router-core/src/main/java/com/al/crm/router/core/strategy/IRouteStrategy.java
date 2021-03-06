package com.al.crm.router.core.strategy;

/**
 * 路由策略
 */
public interface IRouteStrategy {
    /**
     * 确定当前使用连接客户端实例名称
     * @return routeKey
     */
    String determineCurrentRouteName();
}
