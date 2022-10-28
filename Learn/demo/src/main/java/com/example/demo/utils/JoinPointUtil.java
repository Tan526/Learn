package com.example.demo.utils;

import org.aspectj.lang.JoinPoint;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class JoinPointUtil {

    /**
     * 通过joinpoint获取指定的方法
     * @param joinPoint
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Method getMethodByJoinPoint(JoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
        Method method = null;
        // 该注解为方法上的注解，所以实例为MethodInvocationProceedingJoinPoint类型，其中包含ProxyMethodInvocation类型的常量
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            // 强转为MethodInvocationProceedingJoinPoint类型
            MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) joinPoint;
            // 利用反射获取实例中的MethodInvocation属性，即获取ProxyMethodInvocation类型的属性
            Object fieldValue = GetInstance.getFieldValue(mjp, MethodInvocationProceedingJoinPoint.class, "methodInvocation");
            // 如果属性值的运行时类为ProxyMethodInvocation类型，即强转为该类型
            if (fieldValue instanceof ProxyMethodInvocation) {
                ProxyMethodInvocation methodInvocation = (ProxyMethodInvocation) fieldValue;
                method = methodInvocation.getMethod();
            }
        }
        return method;
    }

    /**
     * 获取方法上注解的memberValues
     * @param method
     * @param annotation
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> getAnnotationValuesFromMethod(Method method, Annotation annotation) throws NoSuchFieldException, IllegalAccessException {
        // 获取注解的属性名与属性值构成的memberValues（注解上的属性名属性值是由memberValues管理的）
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        // 利用反射获取memberValues
        Field value = invocationHandler.getClass().getDeclaredField("memberValues");
        value.setAccessible(true);
        Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
        return memberValues;
    }
}
