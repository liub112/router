package com.al.crm.router.nosql;

import com.al.crm.nosql.cache.IRedisFix;
import com.al.crm.router.core.AbstractClientRouter;

import java.util.HashMap;
import java.util.Map;

public class DefaultCacheClientRouter extends AbstractClientRouter<IRedisFix> {
    protected IRedisFix resolveSpecifiedClient(Object o){
        if(!(o instanceof IRedisFix)){
            throw  new IllegalArgumentException("指定的nosql实现类："+ o.getClass().getName()
                    +" 不正确，原因：必须实现接口：com.al.crm.nosql.cache.IRedisFix");
        }
        return (IRedisFix)o;
    }
}
