package com.tan.testreturn.aspectj;

import java.lang.reflect.Method;

//import com.DbSelector;
//import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/** 注解切面
*/  
@Aspect
@Order(value = 1)
public class DbAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Around("@annotation(com.DbSelector)")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//
//        DbSelector ds = method.getAnnotation(DbSelector.class);
//        if (ds == null) {
//            RoutingDataSource.setDataSourceKey("default");//应设置为用户地区数据库
//            System.out.println("设置数据源：用户地区数据库");
//        } else if(StringUtils.trimToNull(ds.region())==null){
//        	//应设置为用户地区数据库
//        }else {
//        	RoutingDataSource.setDataSourceKey(StringUtils.trimToNull(ds.region()));
//        	System.out.println("设置数据源： " + (ds.region()==null?"":"region="+ds.region()+";"));
//        }
//
//        try {
//            return point.proceed();
//        } finally {
//        	RoutingDataSource.clearDataSourceKey();
//        	System.out.println("复位数据源为默认");
//        }
//    }
}

