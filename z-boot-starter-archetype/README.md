# 多模块脚手架

### 前言

​	脚手架（`maven archetype`）开发的目的实际上是为了使我们的项目快速进入开发阶段而不必在搭建框架上花太多的功夫，但是其作为一个模板，并不能满足每一个项目每一种开发风格，因此，本项目的介绍将以教程的方式介绍如何搭建一个适合自己开发团队的脚手架，同时也会分享一些自己在研究过程中踩到的坑以及解决方案。希望大家在看完本篇介绍后能有所收获。

​	为保持教程的简单，本脚手架省略了用于编写测试用例的test包，有需要的请自行在自己的项目中添加。

### 介绍

​	脚手架的搭建有两种方式，一种是通过插件`maven-archetype-plugin`生成，另一种是直接按照maven官网介绍的脚手架设计固定格式撸代码。本教程介绍后者，前者请自行搜索其他教程。

​	接下来我将带大家简单的熟悉一下脚手架的搭建方式，如果你觉得你懒，那你不用自己去[maven官网](<http://maven.apache.org/guides/mini/guide-creating-archetypes.html>)找教程了，跟着我走就能了解了，当然，我更推荐你能够前往官网地址解锁更多的姿势。

#### 脚手架结构

 1. 一个外层的项目壳子，称之为archetype，这个壳子的功能仅仅是像是我们普通项目的pom一样用于定位到本脚手架，理解成一个贴了标签的盒子好了(后文简称为盒子)。
    	于是我创建了一个maven项目称之为：`z-boot-starter-archetype`，然后删除一些不必要的包，最后的结构如下：

    ```
    |-- z-boot-starter-archetype //脚手架模块名
    |-- |-- src
    |-- |-- |-- main
    |-- |-- |-- |-- resources
    |-- |-- pom.xml //*请理解成贴在盒子上用于定位的标签
    ```

 2. 在盒子中我们能看到除了pom文件仅仅是留下了延伸到resources也就是我们平时编写配置文件的目录，在这个目录下，我们将对脚手架的核心内容进行创建。

    ​	resources目录下的结构必须固定，包含包名文件名的拼写不得有误，没信心的建议复制粘贴，这会让你直接跳过因为META写成MATE而一天都没有搭建成功的坑。

    ```
    |-- resources //*固定包名 注意书写
    |-- |-- archetype-resources //*固定包名 注意书写
    |-- |-- |-- __rootArtifactId__-api //占位符-api模块
    |-- |-- |-- |-- src
    |-- |-- |-- |-- |-- main
    |-- |-- |-- |-- |-- |-- java
    |-- |-- |-- |-- |-- |-- |-- api
    |-- |-- |-- |-- |-- |-- |-- |-- controller
    |-- |-- |-- |-- pom.xml //模块pom
    |-- |-- |-- __rootArtifactId__-common //占位符-common模块
    |-- |-- |-- |-- pom.xml //模块pom
    |-- |-- |-- __rootArtifactId__-service //占位符-service模块
    |-- |-- |-- |-- pom.xml //模块pom
    |-- |-- |-- __rootArtifactId__-business //占位符-business模块
    |-- |-- |-- |-- pom.xml //模块pom
    |-- |-- |-- __rootArtifactId__-dal //占位符-dal模块
    |-- |-- |-- |-- pom.xml //模块pom
    |-- |-- |-- pom.xml //*请理解成父pom
    |-- |-- META-INF //*固定包名 注意书写
    |-- |-- |-- maven //*固定包名 注意书写
    |-- |-- |-- |-- archetype-metadata.xml //*固定文件名 注意书写 用于定义生成脚手架时，将携带哪些信息
    
    
    占位符解释：
    以下占位符你都可以在创建项目结构时使用
    __rootArtifactId__	将被生成项目时定义的ArtifactId占位，固定写法
    __packageInPathFormat__	(不推荐)将被生成项目时定义的GroupId占位(通常情况下，我们应当在archetype-metadata.xml中定义package变量，而不使用此占位符)，固定写法
    __artifactId__	(不推荐)将被archetype-metadata.xml文件中对应model标签下的id属性代替(通常该属性书写方式被-隔开，因此实际开发中建议此处不要使用占位符，而是和我一样一层层手动创建包名)，固定写法
    ```

    ​	以上结构中打了*****的便是脚手架核心框架的必须内容，其他内容按需增减即可

 3. 以上便是所有maven官网介绍的脚手架必须的框架了，咱们还是看一下官网的介绍

    ```
    ① an archetype descriptor(archetype-metadata.xml in directory: src/main/resources/META-INF/maven/) - 即我描述的archetype-metadata.xml文件是必须的
    ② the prototype files that are copied by the archetype plugin (directory: src/main/resources/archetype-resources/) - 即我描述的resources目录是必须的
    ③ the prototype pom (pom.xml in: src/main/resources/archetype-resources) - 即我描述的2中父pom是必须的
    ④ a pom for the archetype (pom.xml in the archetype's root directory) - 即我描述的1中的定位标志pom是必须的
    ```

    看不过瘾请移步maven官网针对[Creating Archetypes](<http://maven.apache.org/guides/mini/guide-creating-archetypes.html>)的介绍。

#### archetype-metadata

​	根据官网介绍，本文件将列出原型中包含的所有文件并对这些文件进行分类，使得在原型工作的时候能够正确地处理它们。

​	概念比较模糊，我贴上一份本项目的`archetype-metadata.xml`文件删减版内容，随后对其中的各个字段进行解释

```
<archetype-descriptor
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0
        http://maven.apache.org/xsd/archetype-descriptor-1.0.0.xsd"
        xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-descriptor/1.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        name="z-boot-starter-archetype">

    <modules>
        <module id="${rootArtifactId}-api" dir="__rootArtifactId__-api">
            <fileSets>
                <fileSet filtered="true" packaged="true" encoding="UTF-8">
                    <directory>src/main/java</directory>
                    <includes>
                        <include>**/*.java</include>
                    </includes>
                </fileSet>
                <fileSet filtered="true" encoding="UTF-8">
                    <directory>src/main/resources</directory>
                    <includes>
                        <include>**/*.xml</include>
                        <include>**/*.properties</include>
                    </includes>
                </fileSet>
            </fileSets>
        </module>
        <module id="${rootArtifactId}-service" dir="__rootArtifactId__-service">
            <fileSets>
                <fileSet filtered="true" packaged="true" encoding="UTF-8">
                    <directory>src/main/java</directory>
                    <includes>
                        <include>**/*.java</include>
                    </includes>
                </fileSet>
            </fileSets>
        </module>
    </modules>
</archetype-descriptor>

字段解释：
	archetype-descriptor标签中的name字段为最终生成的盒子pom标签中的description属性，建议与盒子的ArtifactId保持一致
	module标签中的id字段如：${rootArtifactId}-api拼接结果将作为api模块pom中的${artifactId}占位符内容
	module标签中的dir字段如：__rootArtifactId__-api拼接结果将用于寻找脚手架中的模块名，若无对应的模块名，生成的模板项目将不正常
	fileSet标签下的packaged属性，会在生成的项目中自动创建变量package表示的包名结构，默认与创建项目时定义的groupId相同，但是我更加建议自己定义此变量。当packaged=true时，将在directory属性值后拼接上package变量定义的包结构，因此一般情况下，package值在java源程序包中使用，resources不使用
	其他标签字段没有什么特殊含义，不做特殊解释
```

​	本文件中同时还可以进行变量定义，如默认值的定义，当我们在使用mvn命令使用脚手架时，可跳过输入一些信息而使用默认值，使用方式如：`${groupId}`配置参考：

```
    <requiredProperties>
        <requiredProperty key="groupId">
            <defaultValue>com.seven</defaultValue>
        </requiredProperty>
        <requiredProperty key="artifactId">
            <defaultValue>seven-archetype</defaultValue>
        </requiredProperty>
        <requiredProperty key="package">
            <defaultValue>com.seven.boot.archetype</defaultValue>
        </requiredProperty>
    </requiredProperties>
```

​	如果你需要在脚手架中加入.ignore文件以让所有项目的ignore范围一致，请直接使用`<fileSets>`标签即可，配置方法略。更多姿势请到[ArchetypeDescriptor](http://maven.apache.org/archetype/archetype-models/archetype-descriptor/archetype-descriptor.html)进行解锁

### 核心内容

​	到此之前，仅仅是对脚手架的框架以及生成进行了定义，到此，你只需要在盒子根目录下执行`mvn clean install`即可得到一份空的脚手架原型了。

​	但是，实际制作脚手架的过程中，我们往往需要脚手架中有我们想要的包以及一些固定的类、工具等信息。接下来将以`__rootArtifactId__-api`模块为示例针对脚手架的核心内容的创建进行说明。

​	此处我建议你配合着源码了解，我不会在这里贴上大篇幅代码内容，我会告诉你我正在解释哪个文件，你**需要打开源码并打开我描述的文件**并跟着理解。

I 文件：`archetype-resources\pom.xml`

```
1. ${groupId} 根据脚手架生成项目时要求填写的groupId
2. ${artifactId}-parent 根据脚手架生成项目时要求填写的artifactId，我后边加了-parent是个人习惯，你也可以不用加，但是一旦加上，就表示你的父pom的最终artifactId是拼接后的结果
3. ${version} 根据脚手架生成项目时要求填写的version
4. <modules>标签下对于子模块的描述
	${rootArtifactId}-api 表示使用脚手架生成项目时要求填写的artifactId拼接上-api表示自己的api子模块。当然，你也可以使用${artifactId}-api代替
	其他子模块定义同理
5. 其他的内容没有特别强调，你只需要按照你需要的依赖进行添加即可。
```

II 文件：`__rootArtifactId__-api\pom.xml`

这里边有**重点**，请仔细阅读并尝试理解，本pom将作为所有子模块的通用讲解，将不再对其他模块细讲。

```
1. 关于<parent>标签有必要进行解释，<parent>标签中的<artifactId>请指定为${rootArtifactId}-parent，此处不能使用${artifactId}代替${rootArtifactId}。
	这里不得不提${rootArtifactId}与${artifactId}的区别，在父pom中，由于没有模块的概念，所以这两个占位符表示的都是同一个值，也就是创建项目时输入的artifaceId值，因此在archetype-resources\pom.xml中这两个占位符你可以相互替换。但是在子模块中，${artifactId}表示的是archetype-metadata.xml文件中每一个model标签下的id属性(也就意味着每个子模块中的相同${artifactId}占位符代表的是不同的值)，但是${rootArtifactId}从始至终表示创建项目时输入的artifaceId值。
2. 其他的用法就没有特殊说明的
```

III 文件`${package}.ApiApplication.java`

本类将作为启动类，也会对一些特殊的地方进行说明，我相信理解这个类的用法后，其他类、包、文件的创建方式将不在话下

```
1. ${package} 取值来源为archetype-metadata.xml文件中对于package变量的定义。当无定义时默认本占位符将被创建项目时的groupId代替，你也可以理解成最初讲项目框架时，java包下的__packageInPathFormat__内容,同样__packageInPathFormat__的值将保持全局唯一。
2. import 的内容是根据实际开发中的import复制过来的，按需使用即可。
3. 还需要说明的是，在给每一个类编写package时，请仔细思考自己的用法是否正确，项目生成之后，该类和package是否能够恰好的对应上。没有信心的话，多做几次实验即可
```

### 结束

一个脚手架的搭建就在这里结束了，如果跟着本文档你没有成功做出一个属于你的脚手架，请一定不要怀疑自己甚至放弃，肯定是我在某个地方没有描述清楚，因为脚手架本身的技术含量并不高，只是有些坑确实需要踩一踩。

当然，如果你需要帮助，你大可以发起issue或者评论，我将会在读到消息的第一时间给予必要的答复。

### 最后

​	要编写出一份完美的脚手架，除了适合自己的项目以外，有非常多的细节需要大家在使用的过程中发现并处理好，相信每个人都能从中有所收获。

​	如果跟着本教程你还是没有搭建成功，我给大家一些排错case，请根据我提供的case严格检查：

1. 检查各个包名是否存在漏字母，错字母，字母顺序不正确的情况，主要检查我在最开始描述框架的时候标了*的那部分

   META-INF 是否写成了MATE-INF

   META-INF 是否写成了META-INFO

   archetype-metadata.xml 是否拼错

   archetype-resources包名是否拼错，漏s了吗

2. archetype-metadata.xml文件中module标签下的dir字段是否在脚手架中都能找到对应的模块名与之对应

3. 各个pom中的标签是否存在重复

4. 代码更新后是否在脚手架根目录执行过mvn install

   我所能想到可能导致你失败的原因都在此处了，如果你在使用过程中还遇到其他原因，非常希望能够给我一些反馈，同时也给其他使用本文档的伙伴使用到你提供的排错case。

### 一些你可能会使用到的命令

如果你未使用脚手架插件的话，我想这些命令是你需要的

```
mvn clean install 打包脚手架项目到本地仓库
```

```
mvn archetype:generate 创建脚手架项目，该命令会罗列出所有的包括远程仓库提供的脚手架供你选择，但是数量实在是太多了，一般不使用
```

```
mvn archetype:crawl 扫描本地存在的脚手架(archetype)，并更新到位于你定义的本地仓库根目录的archetype-catalog.xml文件中
```

```
mvn archetype:generate -DarchetypeCatalog=${xxx} 创建脚手架项目，${xxx}为local时会罗列出所有本地存在的脚手架项目，实际上本命令的数据来源就是上条命令提到的archetype-catalog.xml。所以如果你有脚手架更新，建议先执行下mvn archetype:crawl。
	${xxx}为local时会罗列出所有本地存在的脚手架项目，即~/.m2/repository/archetypeCatalog文件
	${xxx}为internal时扫描maven-archetype-plugin内置的archetypeCatalog文件
	${xxx}为remote时扫描Maven中央仓库的archetypeCatalog文件
```

```
mvn archetype:generate                                  \
  -DarchetypeGroupId=<archetype-groupId>                \	//原型的groupId
  -DarchetypeArtifactId=<archetype-artifactId>          \	//原型的artifactId
  -DarchetypeVersion=<archetype-version>                \	//原型的version
  -DgroupId=<my.groupid>                                \	//将创建的项目groupId
  -DartifactId=<my-artifactId>								//将创建的项目artifactId
  -Dpackage=<my-package>									//将创建的项目package
  
如果你不想通过idea工具创建脚手架项目(idea工具创建不能指定package)，这个命令可以帮助你通过命令的方式创建它。
```



