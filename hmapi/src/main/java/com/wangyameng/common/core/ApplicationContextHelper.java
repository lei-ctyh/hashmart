package com.wangyameng.common.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0
 * @description: 某些特殊的场景下，需要获取spring容器中的bean，
 * 但是spring容器中的bean是通过反射获取的，所以需要使用ApplicationContextHelper来获取bean
 * 注意：
 * 1. 该类不能被spring管理，否则会导致循环依赖
 * 2. 该类不能被spring扫描，否则会导致spring扫描不到该类
 * 3. 该类不能被spring扫描，否则会导致spring扫描不到该类
 * @date 2023/11/9 22:34
 */
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T popBean(String name, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}
