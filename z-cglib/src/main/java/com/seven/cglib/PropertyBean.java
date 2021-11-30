package com.seven.cglib;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @decs:
 * @program: z-parent
 * @author: zhangxianwen
 * @create: 2021/4/2 12:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyBean {

    private String key;
    private Object value;
}
