package com.seven.dynamic.datasource.api.controller;

import com.seven.bootstarter.dynamicdatasource.annotation.SwitchDataSource;
import com.seven.dynamic.datasource.dal.seven1.dataobject.User;
import com.seven.dynamic.datasource.dal.seven2.dataobject.Product;
import com.seven.dynamic.datasource.service.DataTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 类级别手动指定数据源
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/11 10:32
 **/
@Api(tags = "手动指定数据源-类")
@RestController
@SwitchDataSource("seven1")
@RequestMapping("/dynamic-datasource/class")
public class ClassSwitchDatasourceAnnotationTest {
    @Autowired
    @Qualifier("userService")
    private DataTestService<User> userService;

    @Autowired
    @Qualifier("productService")
    private DataTestService<Product> productService;

    @ApiOperation(value = "seven1-user插入", notes = "由于本类指定的数据源为seven1，本条请求将成功")
    @GetMapping("/insertUser")
    public void insertUser() {
        User user = new User();
        user.setName("seven");
        user.setAddress("shanghai");
        userService.insert(user);
    }


    @ApiOperation(value = "seven2-product插入", notes = "由于本类指定的数据源为seven1，本条请求将失败")
    @GetMapping("/insertProduct")
    public void insertProduct() {
        Product product = new Product();
        product.setName("book");
        product.setPrice(BigDecimal.valueOf(89));
        productService.insert(product);
    }
}
