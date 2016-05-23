package com.mgfinal.core.mybatis.ds;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * mybatis集成c3p0数据源
 * @author mg
 * @date 2016-5-10
 */
public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {
	public C3P0DataSourceFactory() {
		this.dataSource = new ComboPooledDataSource();
	}
}
