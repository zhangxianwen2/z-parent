# 服务开关测试平台

## 介绍

本`demo`将演示如何使用服务开关`starter`，本`demo`配置了`apollo`服务

## 写在前面

本`demo`依赖于`apollo`服务，我在本地是使用的`apollo`官方提供的[quick start](https://github.com/ctripcorp/apollo/wiki/Quick-Start)，如果你没有可用的`apollo`服务，我推荐你跟着该官方教程搭建自己本地使用的`apollo`服务。你必须在拥有可用的`apollo`服务后，才能使用到本`starter`服务开关的功能。

## 项目解释

1. web项目针对外界请求需要做服务的拦截，通过@Switcher注解

   ```
   package com.seven.bootstarter.switcher.platform.controller;
   
   @Slf4j
   @RestController
   @RequestMapping("/switch")
   public class SwitcherController {
       @GetMapping("/test")
       @Switcher(value = "testSwitcher")
       public void testSwitcher() {
           log.info("testSwitcher开关未生效");
       }
   }
   ```

2. 拦截基于单个接口

   ```
   @GetMapping("/test")
   @Switcher(value = "testSwitcher")
   public void testSwitcher() {
       log.info("testSwitcher开关未生效");
   }
   @GetMapping("/test2")
   @Switcher(value = "testSwitcher2")
   public void testSwitcher2() {
       log.info("testSwitcher2开关未生效");
   }
   ```

3. 对于被拦截的信息，进行自定义处理。定义`controller`加强类，全局捕获`ServerUnableException`异常，并进行自定义的处理

   ```
   package com.seven.bootstarter.switcher.platform.handler;
   
   @RestControllerAdvice
   public class BizExceptionHandler {
       @ResponseBody
       @ExceptionHandler(ServerUnableException.class)
       public void handleSwitcherUnableException(ServerUnableException e) {
           log.info("在这里捕捉到了服务停止转用异常，然后自行处理吧！");
           // todo something
       }
   }
   ```

4. 配置信息，取自`apollo`配置

   ```
   z-switcher.switcher.testSwitcher.enable=false
   z-switcher.switcher.testSwitcher.startTime=2020-01-01 00:00:00
   z-switcher.switcher.testSwitcher.startTime=2020-01-02 00:00:00
   z-switcher.switcher.testSwitcher2.enable=false
   z-switcher.switcher.testSwitcher2.startTime=2020-01-01 00:00:00
   z-switcher.switcher.testSwitcher2.startTime=2020-01-02 00:00:00
   ```

   