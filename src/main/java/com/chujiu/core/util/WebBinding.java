package com.chujiu.core.util;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jesse on 2016/7/1/001.
 */
public class WebBinding implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // 使用spring自带的CustomDateEditor
        // 解决时间转换问题
        // yyyy-MM-dd
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
