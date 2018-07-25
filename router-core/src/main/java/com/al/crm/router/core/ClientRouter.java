package com.al.crm.router.core;

/**
 * 缓存客户端路由器
 * @param <T>
 * @author think
 */
public interface ClientRouter<T> {
	
	public T determineCurrentClientBean();

}
