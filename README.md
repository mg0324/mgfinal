# mgfinal
基于mgwork（servlet3.0多实例非单例）做mvc框架，
mybatis做orm框架，
mgioc做单例容器的mgfinal框架，
mgutil做日志及常用工具方法管理，
致力于打造实用的java后台底层。<br/>
适用于中小型应用，及研究框架底层学习爱好者。

##关于作者
一个喜爱代码的狂热工作者，常用名梦网，梦来梦往，mg0324等。<br/>
QQ:1092017732<br/>
github:https://github.com/mg0324<br/>
gitoschina:https://git.oschina.net/mgang<br/>

如果觉得不错的话，别忘了给个star咯。thanks.<br/>

##相关包
mgwork-ioc-1.1.jar mgwork-ioc框架集合（mvc + ioc）<br/>
	--fastjson.jar json解析包<br/>
	--freemarker.jar 视图包<br/>
mybatis-xx.jar 	   mybatis框架(orm)<br/>
	--jsqlparser.jar <br/>
	--pagehelper.jar mybatis分页插件<br/>

##更新日志
###2016-5-3
1.继承mgwork-2.0 和 mybatis 3.* 到mgfinal框架中。<br/>
2.搭建mybatis底层，初步增加丰富的查询封装。
(selectOne)，(selectList)，(selectMap)，(selectListMap)，(selectForInt)，(selectForString)等<br/>
3.集成PageHelper来实现mybatis分页，新增分页案例及方法(selectPage).<br/>

###2016-5-4
1.整合mgwork-ioc框架，测试通过。<br/>
2.添加2个方法executeUpdate和executeQuery 通用的crud方法。<br/>
3.正在想办法实现事务控制！

###2016-5-10
1.完成事务控制，找到之前的原因（mysql的配置my.ini，未使用innodb的引擎，导致不支持事务。）找到原因后，事务支持成功。<br/>

	//开启事务
	this.demoDao.start();
	//操作1
	this.demoDao.ddlTx("com.mgfinal.vo.Demo.addDemo", p);
	//操作2
	this.demoDao.ddlTx("com.mgfinal.vo.Demo.addDemo", p);
	//提交事务
	this.demoDao.end();

2.start,end的事务，放到master主分支中。另外还有一个基于SqlHelper先获取sql,然后conn来commit,rollback的事务支持提交到了v0.1分支。<br/>
3.修改方法名称 executeUpdate -> ddl , executeQuery -> query , executeUpdateWithTx -> ddlTx，只要是简单明了。<br/>
4.添加数据源支持常用数据源支持driud,c3p0等。<br/>
c3p0:<br/>
①添加jar包支持c3p0-0.9.5.1.jar和mchange-commons-java-0.2.10.jar<br/>
②mybatis.xml配置文件中，配置数据源如下

	<dataSource type="com.mgfinal.core.mybatis.ds.C3P0DataSourceFactory">
        <property name="driverClass" value="${driver}" />
        <property name="jdbcUrl" value="${url}" />
        <property name="user" value="${username}" />
        <property name="password" value="${password}" />
        <property name="idleConnectionTestPeriod" value="60" />
        <property name="maxPoolSize" value="20" />
        <property name="maxIdleTime" value="600" />
        <property name="preferredTestQuery" value="SELECT 1" />
    </dataSource>
driud:<br/>
①添加jar包支持druid-1.0.5.jar<br/>
②mybatis.xml配置文件中，配置数据源如下

	<!-- druid数据源 -->
    <dataSource type="com.mgfinal.core.mybatis.ds.DruidDataSourceFactory">
    	<!-- 基本属性 url、user、password -->  
    	<property name="driver" value="${driver}" />
	    <property name="url" value="${url}" />  
	    <property name="username" value="${username}" />  
	    <property name="password" value="${password}" />  
	    <!-- 配置初始化大小、最小、最大 -->  
	    <property name="initialSize" value="1" />  
	    <property name="minIdle" value="1" />   
	    <property name="maxActive" value="20" />  
	    <!-- 配置获取连接等待超时的时间 -->  
	    <property name="maxWait" value="60000" />  
	    <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->  
	    <property name="timeBetweenEvictionRunsMillis" value="60000" />  
	    <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->  
	    <property name="minEvictableIdleTimeMillis" value="300000" />  
	    <property name="validationQuery" value="SELECT 'x'" />  
	    <property name="testWhileIdle" value="true" />  
	    <property name="testOnBorrow" value="false" />  
	    <property name="testOnReturn" value="false" />  
	    <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->  
	    <property name="poolPreparedStatements" value="true" />  
	    <property name="maxPoolPreparedStatementPerConnectionSize" value="20" />  
    </dataSource>
5.升级mgwork-ioc-1.2.jar，将日志常用操作丢给mgutil来管理。<br/>
6.后续可能会增加单表基于对象的CRUD通用操作。
待续...
