package com.seven.dynamic.datasource.api.controller;

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
 * 自动选择数据源
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 15:41
 **/
@Api(tags = "自动选择数据源")
@RestController
@RequestMapping("/dynamic-datasource")
public class NoSwitchDatasourceAnnotationTest {

    @Autowired
    @Qualifier("userService")
    private DataTestService<User> userService;

    @Autowired
    @Qualifier("productService")
    private DataTestService<Product> productService;


    @ApiOperation(value = "user插入", notes = "将自动选择seven1-user")
    @GetMapping("/insertUser")
    public void insertUser() {
        User user = new User();
        user.setName("seven");
        user.setAddress("shanghai");
        userService.insert(user);
    }

    @ApiOperation(value = "product插入", notes = "将自动选择seven2-product")
    @GetMapping("/insertProduct")
    public void insertProduct() {
        Product product = new Product();
        product.setName("book");
        product.setPrice(BigDecimal.valueOf(89));
        productService.insert(product);
    }
}
