package com.echo.commons.utils;

import com.echo.commons.service.IDGenService;
import org.springframework.beans.factory.FactoryBean;

/**
 * Id生成
 *
 * @author Echo-cxt
 * @create 2017-11-08 6:18 PM
 **/
public class IdMaker implements FactoryBean<Object>, IDGenService {

    private static IDGenService instance = new IdMaker64();

    public IdMaker() {}

    @Override
    public String getID() {
        return generate();
    }

    @Override
    public Object getObject() throws Exception {
        return instance;
    }

    @Override
    public Class<?> getObjectType() {
        return IDGenService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static  String generate(){
        return instance.getID();
    }
}
