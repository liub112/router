package com.al.crm.router.database;

import com.al.crm.router.core.strategy.IRouteStrategy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class SpringDataSourceRouter extends AbstractRoutingDataSource{

    private  Class<IRouteStrategy> strategy;
    /**
     * 路由策略
     */
    private IRouteStrategy routeStrategy;
    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    public void setStrategy(Class<IRouteStrategy> strategy) {
        if(!IRouteStrategy.class.isAssignableFrom(strategy))
            throw new IllegalArgumentException("指定的路由策略实现类："+ strategy.getName()
                    +" 不正确，原因：必须实现接口：com.al.crm.redis.route.IRouteStrategy");
        try{
            this.routeStrategy = strategy.newInstance();
        }catch(IllegalAccessException e){
            throw new IllegalArgumentException("加载路由策略实现类："+ strategy.getName()+" 异常，原因："+e.getMessage());
        }catch(InstantiationException e){
            throw new IllegalArgumentException("实例化路由策略实现类："+ strategy.getName()+" 异常，原因："+e.getMessage());
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return routeStrategy.determineCurrentRouteName();
    }
}
