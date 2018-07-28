package com.al.crm.router.core;

import com.al.crm.router.core.strategy.IRouteStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractClientRouter<T> implements ClientRouter<T>, InitializingBean {

    /**
     * 缓存Client集合
     */
    protected Map<String,T> resloveClientInstanceBeans;

    private  Class<IRouteStrategy> strategy;
    /**
     * 路由策略
     */
    private IRouteStrategy routeStrategy;
    /**
     * 客户端集合
     */
    private Map<String,Object> targetClientInstanceBeans;

    public void setTargetClientInstanceBeans(Map<String, Object> targetClientInstanceBeans) {
        this.targetClientInstanceBeans = targetClientInstanceBeans;
    }

    public void setStrategy(Class<IRouteStrategy> strategy) {
        if(!IRouteStrategy.class.isAssignableFrom(strategy))throw new IllegalArgumentException("指定的路由策略实现类："+ strategy.getName()+" 不正确，原因：必须实现接口：com.al.crm.redis.route.IRouteStrategy");
        try{
           this.routeStrategy = strategy.newInstance();
        }catch(IllegalAccessException e){
            throw new IllegalArgumentException("加载路由策略实现类："+ strategy.getName()+" 异常，原因："+e.getMessage());
        }catch(InstantiationException e){
            throw new IllegalArgumentException("实例化路由策略实现类："+ strategy.getName()+" 异常，原因："+e.getMessage());
        }
    }

    @Override
    public T determineCurrentClientBean() {
        String name;
        try{
            name= routeStrategy.determineCurrentRouteName();
            Assert.notNull(name,"请指定当前路由名称");
            if(!this.resloveClientInstanceBeans.containsKey(name))throw new IllegalArgumentException("clientSource："+name+" is not exist!");
            RouteContext.setName(name);
            return this.resloveClientInstanceBeans.get(name);
        }finally{
            name=null;
        }
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.targetClientInstanceBeans == null){
            throw  new IllegalArgumentException("Property 'targetClientInstanceBeans' is required");
        }

        if(this.routeStrategy == null){
            throw  new IllegalArgumentException("Property 'routeStrategy' is required");
        }

        this.resloveClientInstanceBeans = new HashMap<String,T>(targetClientInstanceBeans.size());
        for (Map.Entry<String,Object> entry: this.targetClientInstanceBeans.entrySet()) {
            String lookupKey = entry.getKey();
            Object o = entry.getValue();
            T source = resolveSpecifiedClient(o);
            resloveClientInstanceBeans.put(lookupKey,source);
        }
    }

    protected abstract T resolveSpecifiedClient(Object o);

}
