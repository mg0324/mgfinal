package com.mgfinal.core.ioc.context.inter;
/**
 * ioc接口
 * @author mg
 * @date 2016-5-14
 */
public interface Ioc {
	/**
	 * 添加对象到容器
	 * @param key
	 * @param value
	 */
	public void set(String key,Object value);
	/**
	 * 从容器中获取对象
	 * @param key
	 * @return
	 */
	public Object get(String key);
	/**
	 * 展示容器中的key
	 * @return
	 */
	public String toJsonString();
	/**
	 * 销毁
	 */
	public void destory();
}
