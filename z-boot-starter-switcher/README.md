# 基于apollo的注解式服务开关

## 介绍

​	本模块作为一个`starter`为所有有需要的web服务甚至是任何一个接口提供开启和关闭功能，理论上，你只需要在对应的接口上添加规定的注解，然后再配置文件中添加相应的配置即可实现。

​	实际上，在生产环境或者是测试环境中，我们的开关都必须做到即时生效，仅仅修改配置状态而无需重启服务即可对服务的开启以及关闭状态进行修改，因此，我引入了[携程的apollo分布式配置中心](https://github.com/ctripcorp/apollo)用于实时调整开关状态。这要求你的服务必须支持`apollo`服务。

在依赖本`starter`后，你将会得到以下功能：

```
1. 通过注解的方式为你的接口创建开关
2. 你可以对被拦截(被关闭的服务)的接口进行自定义的后续处理方案
3. apollo修改开关状态后即时生效
4. 给自己的开关设置独立的命名空间，并指定为本starter的生效命名空间
```

## 设计思路

​	① 开放一个Map结构的内存用于存储开关信息，key值为开关id`(switcherId)`，value值为该开关的相关信息对象，包含：`enable`可用状态、`startTime`生效开始时间、`endTime`生效结束时间。在项目启动时，对该对象进行初始化。

​	② 维护一个开关配置监听类，对指定`apollo`命名空间进行开关配置的监控，当监控到开关配置有修改时，对开关Map内容进行更新。

​	③ 在本`starter`中维护了一个开关拦截器，用于拦截被@Switcher注解修饰的方法，被拦截到的@Switcher将携带对应的`switcherId`，根据该`switcherId`获取开关Map中对应的数据，若存在，则取出value值并读取相关字段值判断开关状态。然后做进一步的处理。

​	④ 当发现服务被配置为不可用时，抛出自定义的服务不可用异常供客户端捕获并进行自定义处理。

## 使用方法

1. 添加依赖

   ```
   <dependency>
       <groupId>com.seven</groupId>
       <artifactId>z-boot-starter-switcher</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   ```

2. 添加开关配置信息，如：

   其中开关定义的格式为：`z-switcher.switcher`.`${switcherId}`.`${properties}`

   ```
   # 开关id为testSwitcher的服务状态为false(不可用)
   z-switcher.switcher.testSwitcher.enable=false
   # 定义开关生效的命名空间，多个使用逗号隔开
   z-switcher.namespace=application,xxx
   ```

3. 在需要做开关的方法上添加@Switcher注解并指定相应的value(开关Id)，如：

   ```
   @GetMapping("/test")
   @Switcher(value = "testSwitcher")
   public void testSwitcher() {
       log.info("testSwitcher开关未生效");
   }
   ```

## 实例演示

本starter提供了对应的platform模块对本开关功能进行演示，请移步[服务开关测试DEMO](https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-switcher-platform)

