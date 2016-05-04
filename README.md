# mgfinal
基于mgwork（servlet3.0多实例非单例）做mvc框架，mybatis做orm框架，mgioc做单例容器的mgfinal框架，致力于打造实用的java后台底层。<br/>
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
待续...
