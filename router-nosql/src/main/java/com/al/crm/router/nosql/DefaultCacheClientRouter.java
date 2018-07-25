package com.al.crm.router.nosql;

import com.al.crm.nosql.cache.IRedisFix;
import com.al.crm.router.core.AbstractClientRouter;

import java.util.HashMap;
import java.util.Map;

public class DefaultCacheClientRouter extends AbstractClientRouter<IRedisFix> {
    /**
     * 客户端集合
     */
    private Map<String,Object> targetClientInstanceBeans;

    public void setTargetClientInstanceBeans(Map<String, Object> targetClientInstanceBeans) {
        this.targetClientInstanceBeans = targetClientInstanceBeans;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.targetClientInstanceBeans == null){
            throw  new IllegalArgumentException("Property 'targetClientInstanceBeans' is required");
        }
        this.resloveClientInstanceBeans = new HashMap<String,IRedisFix>(targetClientInstanceBeans.size());
        for (Map.Entry<String,Object> entry: this.targetClientInstanceBeans.entrySet()) {
            String lookupKey = entry.getKey();
            Object o = entry.getValue();
            IRedisFix source = resolveSpecifiedClient(o);
            resloveClientInstanceBeans.put(lookupKey,source);
        }
        super.afterPropertiesSet();
    }

    private IRedisFix resolveSpecifiedClient(Object o){
        if(!(o instanceof IRedisFix)){
            throw  new IllegalArgumentException("指定的nosql实现类："+ o.getClass().getName()
                    +" 不正确，原因：必须实现接口：com.al.crm.nosql.cache.IRedisFix");
        }
        return (IRedisFix)o;
    }
}
