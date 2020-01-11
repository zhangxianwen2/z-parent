package com.seven.bootstart.logger.controller;

import lombok.Data;

/**
 * @author zhangxianwen
 * 2020/1/11 16:46
 **/
@Data
public class LogReq {
    private String url;
    private String content;
}
