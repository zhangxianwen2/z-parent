# 多数据源使用示例

### 介绍

项目地址：[z-boot-starter-dynamic-datasource-test](https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-dynamic-datasource-test)

简单基础的`springboot`项目结构，尽可能简单的展示如何将[所数据源模块](https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-dynamic-datasource)投入使用。

`dynamic-datasource-api` 对外提供接口层

`dynamic-datasource-service` 接口层以及主要逻辑实现层 

`dynamic-datasource-dal` 数据库实例层	

### 使用思路

​	各模块依赖顺序为`api --> service --> dal` `--> z-boot-starter-dynamic-datasource`，由于依赖间的继承关系，将数据源模块由`dal`层依赖。

​	为了方便测试，我在本地`MYSQL`中新建了两个数据库分别为`seven1`和`seven2`并分别建立`user`表和`product`表。通过数据源模块中介绍的手段分别对数据源的使用进行测试。

​	测试角度分别为：

1. 类上通过`@SwitchDataSource`注解指定数据源，观察是否该类中所有方法均路由到指定的数据源
2. 单个方法通过`@SwitchDataSource`注解指定数据源，观察是否该方法路由到指定数据源
3. 不手动指定任何数据源，观察是否自动识别对应数据源以及自动切换对应数据源

### 结果

​	与预期一致,其中部分日志如下：

```
2020-01-11 10:46:11.474  INFO 17560 --- [nio-8000-exec-7] c.s.b.d.DynamicDataSourceContextHolder   : Datasource seven1 set into thread!
2020-01-11 10:46:11.477  INFO 17560 --- [nio-8000-exec-7] c.s.b.d.r.DynamicRoutingDataSource       : DataSource:seven1 is used!
2020-01-11 10:46:11.488  INFO 17560 --- [nio-8000-exec-7] c.s.b.d.DynamicDataSourceContextHolder   : Datasource pool is cleared!
2020-01-11 10:46:39.512  INFO 17560 --- [nio-8000-exec-5] c.s.b.d.DynamicDataSourceContextHolder   : Datasource seven2 set into thread!
2020-01-11 10:46:39.529  INFO 17560 --- [nio-8000-exec-5] c.s.b.d.r.DynamicRoutingDataSource       : DataSource:seven2 is used!
2020-01-11 10:46:39.579  INFO 17560 --- [nio-8000-exec-5] c.s.b.d.DynamicDataSourceContextHolder   : Datasource pool is cleared!
2020-01-11 10:46:51.629  INFO 17560 --- [nio-8000-exec-8] c.s.b.d.DynamicDataSourceContextHolder   : Datasource seven1 set into thread!
2020-01-11 10:46:51.634  INFO 17560 --- [nio-8000-exec-8] c.s.b.d.r.DynamicRoutingDataSource       : DataSource:seven1 is used!
2020-01-11 10:46:51.644  INFO 17560 --- [nio-8000-exec-8] c.s.b.d.DynamicDataSourceContextHolder   : Datasource pool is cleared!
```

