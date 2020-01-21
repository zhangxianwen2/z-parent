package com.seven.dynamic.datasource.dal;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 16:03
 **/
public class MybatisplusCodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //用来获取Mybatis-Plus.properties文件的配置信息
        ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus-seven2");

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //具体目录
        gc.setOutputDir(projectPath + rb.getString("entityModuleName") + "/src/main/java");
        gc.setAuthor(rb.getString("author"));
        gc.setOpen(false);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(rb.getString("dataSourceUrl"));
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(rb.getString("dataSourceName"));
        dsc.setPassword(rb.getString("dataSourcePassWord"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.getString("parentPath"));
        pc.setMapper(rb.getString("mapper"));
        pc.setEntity(rb.getString("entity"));
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + rb.getString("entityModuleName") + "/src/main/resources/" + rb.getString("xmlSourcePath") + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null).setService(null).setServiceImpl(null).setController(null));

        // 策略配置(数据库表配置)
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
//        strategy.entityTableFieldAnnotationEnable(true);
        // 多个表用逗号隔开如：.setInclude("table1","table2") 执行后会覆盖同表之前自动生成的文件，若有老代码需要保留请自行备份
        strategy.setInclude("product");
//        strategy.setSuperEntityColumns("id");   //此处设置为id之后 数据库中的id自增字段就不会生成了 请勿打开
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
