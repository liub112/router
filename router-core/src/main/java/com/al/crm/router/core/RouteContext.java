package com.al.crm.router.core;

public class RouteContext {
	private static final ThreadLocal<String> contextHolder=new ThreadLocal<String>();	
	private static final ThreadLocal<String> routeIdHolder=new ThreadLocal<String>();
	/**
	 * 路由标识前缀
	 */
	public final static String CONST_ROUTE_KEY_PREFIX = "areaId";

	/**
	 * Gets the value of routeId
	 *
	 * @return the value of routeId
	 */
	public static String getRouteId() {
		return routeIdHolder.get();
	}
	/**
	 * Sets the value of routeId
	 *
	 */
	public static void setRouteId(String routeId) {
		 routeIdHolder.set(routeId);
	}
	/**
	 * Sets the value of name
	 *
	 */
	protected static void setName(String name){
		contextHolder.set(name.toString());
	}
	/**
	 * Gets the value of name
	 *
	 * @return the value of name
	 */
	public static String getName(){
		return contextHolder.get();
	}

	public static void clear(){
		contextHolder.remove();
		routeIdHolder.remove();
	}
}
