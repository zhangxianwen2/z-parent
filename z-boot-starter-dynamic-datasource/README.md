# 动态数据源

### 介绍

项目地址：[z-boot-starter-dynamic-datasource](https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-dynamic-datasource)

采用spring提供的`LazyConnectionDataSourceProxy`实现的数据源动态切换，`LazyConnectionDataSourceProxy`代理`DataSource`后，将数据源的连接节点将推迟到`Connection#prepareStatement`之后，我只需要在`Connection#prepareStatement`前使用一定的手段对不同的请求路由到对应的数据源即可

### 支持功能

1. 手动注解式指定数据源，支持类指定和方法指定，同时指定时方法注解优先
2. 自动识别数据库源，当不进行数据源指定时，将按照一定的规则自动选取数据源
3. 集成druid监控，druid相关问题可参考[druid官方文档](https://github.com/alibaba/druid/wiki/常见问题)

### 使用方法

1. 添加依赖：

   ```
   <dependency>
   	<groupId>com.seven</groupId>
   	<artifactId>z-boot-starter-dynamic-datasource</artifactId>
   	<version>1.0-SNAPSHOT</version>
   </dependency>
   ```

2. 增加数据源配置以及druid配置，参考：

   ```
   #
   # 数据库1
   spring.datasource.dynamic.datasource.seven1.data-source-name=seven1
   spring.datasource.dynamic.datasource.seven1.url=jdbc:mysql://localhost:3306/seven-1?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
   spring.datasource.dynamic.datasource.seven1.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.dynamic.datasource.seven1.username=root
   spring.datasource.dynamic.datasource.seven1.password=123456
   #
   # 数据库2
   spring.datasource.dynamic.datasource.seven2.dataSourceName=seven2
   spring.datasource.dynamic.datasource.seven2.url=jdbc:mysql://localhost:3306/seven-2?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
   spring.datasource.dynamic.datasource.seven2.driverClassName=com.mysql.cj.jdbc.Driver
   spring.datasource.dynamic.datasource.seven2.username=root
   spring.datasource.dynamic.datasource.seven2.password=123456
   #
   # druid 服务监控
   spring.datasource.dynamic.druid.stat-view-servlet.enabled=true
   #spring.datasource.dynamic.druid.stat-view-servlet.urlPattern=
   #spring.datasource.dynamic.druid.stat-view-servlet.allow=
   #spring.datasource.dynamic.druid.stat-view-servlet.deny=
   #spring.datasource.dynamic.druid.stat-view-servlet.enable=true
   spring.datasource.dynamic.druid.stat-view-servlet.loginUsername=admin
   spring.datasource.dynamic.druid.stat-view-servlet.loginPassword=123456
   #spring.datasource.dynamic.druid.stat-view-servlet.resetEnable=
   #
   # druid 切面监控
   #spring.datasource.dynamic.druid.aop-patterns=
   spring.datasource.dynamic.druid.web-stat-filter.enabled=true
   ```

3. 创建自己的数据库Mapper等文件，我使用的是[MybatisPlus](https://mp.baomidou.com/ )代码生成器，需要注意的是，无论你采用何种方式生成数据库实例代码，如果想要实现在不手动指定数据源的情况下能够正确获取到对应的数据源，请务必按照以下文件结构创建：

   ```
   |--com.seven.dynamic.datasource.dal
   |	|--seven1
   |	|	|--dataobject
   |	|	|	|__User.java
   |	|	|--mapper
   |	|	|	|__UserMapper.java
   |	|--seven2
   |	|	|--dataobject
   |	|	|	|__Product.java
   |	|	|--mapper
   |	|	|	|__ProductMapper.java
   ```

   其中`seven1`和`seven2`表示不同数据源的`datasourceName`，目录接口为倒数第三级。对应的猜测代码于`DynamicDataSourceInterceptor.jave`中，如下：

   ```
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    String resource = mappedStatement.getResource();
    String[] split = resource.split("/");
    // 目录结构猜测为：*/datasourceName/mapper/*Mapper.java
    String datasource = split[split.length - 3];
   ```

### 源码解读

​	如果你想要了解功能是如何实现的，这里介绍了各个类或者包的功能，希望可以在你阅读的过程中有所帮助：

1. 类`SwitchDataSource.java` 为动态数据源的手动设置提供的注解
2. 类`DynamicDataSourceAspect.java` 针对数据源注解写的切面，将注解指定的数据源设置到上下文中
3. 包`autoconfig` 对本模块其他的需要注册到`bean`中的实例进行汇集，同时指定默认的数据源路由方案
4. 包`autoconfig.stat` 完全复制了`com.alibaba.druid`中的相关类，只是自定义了其中的`@ConditionalOnProperty`值
5. 类`DynamicDataSourceInterceptor.java` `mybatis`拦截器，拦截即将执行的`sql`请求并做相应处理
6. 包`properties` 各实体类，不作特殊说明
7. 包`provider` 数据源的提供者，数据源将根据配置信息在这个地方进行加工
8. 包`routing` 数据源路由的核心方法，在这里重写了开头描述的`Connection#prepareStatement`方法，数据源最终的连接确认就是在这里进行的
9. 类`DynamicDataSourceContextHolder.java` 数据源设置，本类维护了一个本地线程，用于存储本线程使用的数据源名称，最终的数据源确认与该本地线程存储的数据源名称有着直接的关系

### 使用示例

​	为了方便开发者快速使用，提供了[动态数据源测试模块](https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-dynamic-datasource-platform)对本功能进行使用测试，一般而言，开发者只需要进行简单的模仿即可使用到本工具的所有功能。

### TODO

1. 数据库连接密码加密
2. 数据库主从切换
