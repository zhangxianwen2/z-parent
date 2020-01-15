# 日志打印方案

### 介绍

项目地址：`https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-logger`

通用的日志打印方案，支持多种常用的日志打印办法，支持信息参考支持列表；你只需要简单的依赖本jar包，无需任何配置，即可使用到本包的所有功能。当然，本jar包的设计也支持较少的配置信息为自己的日志打印方案实现定制化，看完本介绍文档即可了解。

虽然本jar提供的功能看似能够应付一些应用场景，但是并没有经历过很全面完善正式的生产环境测试，因此我依旧建议你能下载源码后仔细阅读，并做一些定制化功能的调整。同时，我也非常期待你在发现问题或者有哪里我没有讲清楚导致你无法正常使用本功能时在项目的issue下提出，我将会在收到信息的第一时间答复你。

### 支持列表

- 单线程链路日志
- 多线程链路日志
- `RestTempate`请求链路日志
- 日志脱敏，被脱敏的信息包括：电话、身份证号、银行卡号、密码

### 使用方法

1. 添加依赖

   ```
   <dependency>
       <groupId>com.seven</groupId>
       <artifactId>z-boot-starter-logger</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   ```

2. 在你的日志里边添加一些敏感信息试试吧。

### 功能描述

1. #### 单线程链路日志

   ​	使用传统的`MDC`本地线程方案实现，通过实现Filter接口拦截所有请求，并在拦截处在`MDC`中添加`traceId`字段，在每一次打印日志时，都会到`MDC`中获取`traceId`字段跟着正常`message`打印出来，实现最简单的链路追踪。如下面的日志就是我打印出来的其中一行，其中`c05ee4d518d144bd85c84b40ff535f96`就是`traceId`，它将伴随着整个请求的日志被打印出来。

   ​	注意：`[192.168.1.106(z-logger)]` 信息属于`MDC`中维护的`traceSeriesIp`字段，其将在多个机器的互相调用时发挥作用。

   ```
   2020-01-15 21:26:31.936 http-nio-8001-exec-1 INFO [c05ee4d518d144bd85c84b40ff535f96] [192.168.1.106(z-logger)] c.s.b.l.c.LoggerTestController - "这是一条单线程日志"
   ```

2. #### 多线程链路日志

   ​	正常开发过程中，避免不了会使用到多线程，由于`MDC`维护在本地线程中，故当启动子线程时，子线程中将不会存在父线程的`MDC`信息，因此链路断开。解决方案是通过`MDCThreadPoolTaskExecutor`类继承`ThreadPoolTaskExecutor`并重写线程池的`execute`方法，将父线程的`MDC`追加到子线程中，使得子线程可以继续沿用父线程的`MDC`信息从而实现多线程的日志链路。产生的一个副作用就是，在项目中需要开启一个线程池时，必须使用`MDCThreadPoolTaskExecutor.java`创建线程池，否则不会生效。使用方式请在`z-boot-starter-logger-test`中`BizThreadPoolTaskConfig`类查看。

   ​	当然，你也可以模仿`MDCThreadPoolTaskExecutor`类自己做一个线程池使用。

   它的展示形式如下：

   ```
   2020-01-15 21:26:31.936 thread-1 INFO [c05ee4d518d144bd85c84b40ff535f96] [192.168.1.106(z-logger)] c.s.b.l.c.LoggerTestController - "这是thread-1线程日志"
   2020-01-15 21:26:31.936 thread-2 INFO [c05ee4d518d144bd85c84b40ff535f96] [192.168.1.106(z-logger)] c.s.b.l.c.LoggerTestController - "这是thread-2线程日志,我的traceId和线程thread-1一样"
   ```

   

3. #### RestTempate请求链路日志

   ​	正常开发过程中，少不了的是和其他系统的交互，若你们的交互方式是`RestTempate`，且各自的系统都依赖了本jar，那你会发现，你们的`MDC`信息将在`RestTempate`的传输过程中被运送并生效。同时，在跨系统间调用时，我还维护了一个`traceSeriesIp`字段，用于记录请求经过的机器(应用)信息并在日志中打印出来。它的展示形式如下：

   ```
   2020-01-15 21:26:31.936 http-nio-8001-exec-1 INFO [c05ee4d518d144bd85c84b40ff535f96] [192.168.1.106(z-logger)->192.168.1.108(z-logger-salve)] c.s.b.l.c.LoggerTestController - "这是一条跨系统的日志"
   ```

   

4. #### 日志脱敏

   ​	日志脱敏的任何一个企业必须要做到的事情，依赖本jar包之后，这一功能将被实现，我目前对正常的日志信息分为了两类。

    1. message属于`Json`格式

       方案：`message`解析为`JSONObject`或者`JSONArray`，根据键值对Key值和Value值双重校验确定目标字段是否属于敏感信息，并根据命中的敏感信息分类进行加密。

    2. message属于普通字符串，没有规律

       方案：根据标点符号以及空格对整条message进行分隔得到若干个单词，对每个单词进行正则匹配判断是否属于敏感信息并进行加密处理。此处涉及的脱敏逻辑略微复杂，我尽可能地描述清楚我是怎么做的，也希望你能尽量理解：

       ```
       - I 设计两个变量，originStr和targetStr，其中originStr表示用于处理的字符串，targetStr表示最终将生成的字符串
       - II 根据标点符号拆分成若干个单词，targetStr追加被拆分后的split[0]也就是第一个单词调用脱敏方法得到的字符串，脱敏方法中将会对该单词进行判断是否属于敏感信息并进行处理，同时originStr中裁剪掉该单词
       - III 注意，在上一条逻辑中，如果不做其他处理，结果将是缺少原符号仅仅是被每个单词拼接得到的字符串，为了使标点符号能够也被追加，当我发现split[0]是空字符串则可以判定该标点前没有内容，此时则需要把这个标点符号追加到targetStr并在originStr中删除它然后继续重复II,III步骤。
       ```

       

    3. ~~(1和2的混合模式)暂未实现~~

### 输出格式

​	本jar包提供了默认的`logback-spring.xml`用于日志打印，该`xml`中，规定了日志必须输出到控制台和文件中，文件路径以及文件名为：`logs/${appName}/${appName}.log`，值得注意的是，其中的`appName`默认值为`undefinedAppName`，需要你在配置文件中配置`z.logger.application.name=yourApplicationName`方可使用。若你不想使用本默认方案或者本默认方案不适合你，你只需要在自己的项目中也写一个`logback-spring.xml`即可覆盖jar包中的配置。

​	值得注意的是，这里只是定义了输出格式，实际使用过程中并没有限制你的选择，你完全可以在打印控制台时使用`RabbitMQ`的输出方案。

1. 控制台输出（默认打开）

   见`ConsoleLayOut.java`，控制台输出格式模仿了常见的日志打印方案，不同的是在日志中增加了`traceId`和`traceSeriesIp`两个字段实现链路追踪。

2. 文件输出（默认打开）

   见`FileLayOut.java`，查看源码可知文件输出格式与控制台输出格式一致

3. RabbitMQ输出（默认关闭）

   见`RabbitMQLayOut.java`，本输出方案针对需要将日志信息通过`RabbitMQ`输出到`logstash`的需求，输出格式为纯`Json`，方便在其他日志显示平台如：`Kibana`进行解析。本输出方案需要配置`MQ`信息，配置信息参考（相关字段含义参考`RabbitMq`的`AmqpAppender.Java`）：

   ```
   ## elk-rabbit
   elk.rabbitmq.enable=false
   elk.rabbitmq.host=${spring.rabbitmq.host}
   elk.rabbitmq.port=${spring.rabbitmq.port}
   elk.rabbitmq.username=${spring.rabbitmq.username}
   elk.rabbitmq.password=${spring.rabbitmq.password}
   elk.rabbitmq.application-id=${spring.application.name}
   elk.rabbitmq.virtual-host=/logback
   elk.rabbitmq.exchange=topic.loggingExchange
   elk.rabbitmq.routing-key=logback.#
   elk.rabbitmq.connection-name=biz|${spring.application.name}
   ```

### 其他说明

​	在`MDC`中，我还维护了`tranceHostName、tranceHostAddress、tranceAppId、tranceExtraSign`等字段，这些字段将在`RabbitMQ`的日志输出方案中被使用，File和控制台中未使用。其具体含义直译即可，其中`tranceHostAddress、tranceHostName`将被自动从系统获取，另外的字段需要手动配置，配置实例如：

```
# appId 和appName类似用于定位是哪一个服务，但是appId更加强调所有系统唯一
z.logger.application.id=myAppId
# 附加信息
z.logger.application.extra-sign=myExtraSignInfo
```

​	需要说明的是`tranceExtraSign`字段属于附加字段，在其被赋值时，将会与其他信息一同打印出来，作用是当其他含义固定的字段不足以表示某个想要打印在日志中的字段时使用。更多请见`MDCFilter.java`。

### 示例代码

​	为了方便开发者快速了解，我提供了[日志方案测试模块](https://github.com/zhangxianwen2/z-parent/tree/master/z-boot-starter-logger-test)进行使用示范，一般而言，你只需要简单模仿即可使用到本jar的功能。

### TODO

微服务间调用日志链路，如：feign、其他Http请求、grpc等

集成ELK，观察本日志模板在ELK中的表现。实际上本功能我已经自己做了，但是由于只是简单的搭了ELK对本地日志进行了收集，没有太大的参考价值，所以没有写出教程，之后空了出一篇ELK搭建以及配合本日志方案使用的教程。