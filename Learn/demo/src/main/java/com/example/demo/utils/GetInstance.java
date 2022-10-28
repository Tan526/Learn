package com.example.demo.utils;

import java.lang.reflect.Field;

public class GetInstance {

    /**
     * 获取对象，如果对象类型为估计类型
     * @param object
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getInstance(Object object, Class clazz){
        if(object.getClass().equals(clazz)){
            return (T) object;
        }else{
            return null;
        }
    }

    /**
     * 根据给定的属性名，获取实例的该属性值
     * @param o
     * @param clazz
     * @param filedName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Object o, Class clazz, String filedName) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(o);
    }

}
