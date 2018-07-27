package com.al.crm.router.database;

import com.al.crm.router.core.AbstractClientRouter;

import javax.sql.DataSource;

public class CustomDataSourceRouter extends AbstractClientRouter<DataSource>{
    @Override
    protected DataSource resolveSpecifiedClient(Object o) {
        if(!(o instanceof DataSource)){
            throw  new IllegalArgumentException("指定的nosql实现类："+ o.getClass().getName()
                    +" 不正确，原因：必须实现接口：javax.sql.DataSource");
        }
        return (DataSource)o;
    }
}
