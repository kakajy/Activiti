package com.jsyl.activiti.factory;

import com.jsyl.activiti.manager.CustomTaskEntityManager;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

/**
 * @Author chenk
 * @create 2020/1/14 13:37
 */
public class CustomTaskEntityManagerFactory implements SessionFactory {

    private CustomTaskEntityManager customTaskEntityManager;

    @Override
    public Class<?> getSessionType() {
        return customTaskEntityManager.getClass();
    }

    @Override
    public Session openSession() {
        return customTaskEntityManager;
    }
}
