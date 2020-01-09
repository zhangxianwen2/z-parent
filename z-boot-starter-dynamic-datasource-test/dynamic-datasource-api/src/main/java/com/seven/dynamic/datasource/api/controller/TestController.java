package com.seven.dynamic.datasource.api.controller;

import com.seven.dynamic.datasource.dal.seven1.dataobject.User;
import com.seven.dynamic.datasource.dal.seven2.dataobject.Product;
import com.seven.dynamic.datasource.service.DataTestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 15:41
 **/
@Api(tags = "动态数据源")
@RestController
@RequestMapping("/dynamic-datasource")
public class TestController {

    @Autowired
    @Qualifier("userService")
    private DataTestService<User> userService;

    @Autowired
    @Qualifier("productService")
    private DataTestService<Product> productService;

    @GetMapping("/insertUser")
    public void insertUser() {
        User user = new User();
        user.setName("seven");
        user.setAddress("shanghai");
        userService.insert(user);
    }


    @GetMapping("/insertProduct")
    public void insertProduct() {
        Product product = new Product();
        product.setName("book");
        product.setPrice(BigDecimal.valueOf(89));
        productService.insert(product);
    }
}
