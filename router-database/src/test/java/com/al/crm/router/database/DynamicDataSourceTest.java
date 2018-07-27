package com.al.crm.router.database;

import com.al.common.context.PropertyHolder;
import com.al.crm.router.core.MDA;
import com.al.crm.router.core.RouteContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DynamicDataSourceTest extends BaseJunitTest{
    @Autowired
    @Qualifier("dataSource")
    private DataSource springDataSourceRouter; //spring 自带的AbstractRoutingDataSource 动态数据源子类实现
    @Autowired
    private InvocationHandler invocationHandler; //通过动态代理实现

    @Test
    public void testSpringDataSourceRouter() throws SQLException {
        System.out.println("SpringDataSourceRouter:");
        dynamicDataSourceDerector(springDataSourceRouter);
    }


    @Test
    public void testDataSourceInvocationHanlder() throws SQLException {
        DataSource proxy = (DataSource) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{
                DataSource.class},invocationHandler);
        System.out.println("DataSourceInvocationHanlder:");
        dynamicDataSourceDerector(proxy);
    }

    private void dynamicDataSourceDerector(DataSource dataSource) throws SQLException {
        PropertyHolder.setProperties("uniconfig.domain","CRM2013");
        Map<String,String> map = new HashMap<String, String>();
        map.put("areaId_832","ds01");
        map.put("areaId_844","ds02");
        map.put("areaId_845","ds01");
        MDA.REGION_ROUTE_MAP.put("CRM2013",map);
        RouteContext.setRouteId("8320100");
        System.out.println(RouteContext.getRouteId()+"'datasouce is :"+dbData(700000980954l,dataSource));
        RouteContext.setRouteId("8440100");
        System.out.println(RouteContext.getRouteId()+"'datasouce is :"+dbData(700000981119l,dataSource));
        RouteContext.setRouteId("8450100");
        System.out.println(RouteContext.getRouteId()+"'datasouce is :"+dbData(700000980954l,dataSource));
        RouteContext.clear();
    }

    private String dbData(Long custOrderId,DataSource dataSource) throws SQLException {
        Connection conn = null;
        String custSoNumber = null;
        try {
            conn = dataSource.getConnection();
            String sql ="select cust_so_number,common_region_id from customer_order where cust_order_id="+custOrderId;
            PreparedStatement prtm = conn.prepareStatement(sql);
            ResultSet rs = prtm.executeQuery();
            StringBuilder  sb = new StringBuilder();
            while(rs.next()){
                custSoNumber = rs.getString(1)+"["+rs.getString(2)+"]";
            }
            return custSoNumber;
        }finally {
            if(conn!=null){
                conn.close();
            }
        }
    }

}
