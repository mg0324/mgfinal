package com.mgfinal.core.mybatis.ds;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import com.mgfinal.log.MgLog;
/**
 * mybatis集成druid数据源
 * @author mg
 * @date 2016-5-10
 */
public class DruidDataSourceFactory implements DataSourceFactory {
    private javax.sql.DataSource dataSource;
 
    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
 
    @Override
    public void setProperties(final Properties props) {
        try {
            this.dataSource = com.alibaba.druid.pool.DruidDataSourceFactory.createDataSource(props);
            MgLog.log.info("mgfinal druid数据源初始化成功！");
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException("mgfinal druid数据源初始化失败", e);
        }
    }
}
