package com.example.demo.advice.aop;

import com.alibaba.fastjson2.JSON;
import com.example.demo.annotation.ReturnMore;
import com.example.demo.utils.JoinPointUtil;
import com.example.demo.utils.Result;
import com.sun.istack.internal.NotNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class ReturnMoreHandle {

    @Pointcut("@annotation(com.example.demo.annotation.ReturnMore)")
    public void myPointCut(){}

    /**
     * 判断是否需要返回JSON数据
     */
//    @Before("myPointCut()")
    public void judgeNeedReturnJSON(JoinPoint joinPoint) {
        // 获得方法的所有参数
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        // 遍历所有参数，获取类型为HttpServletRequest的入参
        for (Object o : args) {
            if (o instanceof HttpServletRequest) {
                request = (HttpServletRequest) o;
            }
        }
        // 如果没有request入参，则直接返回
        if (null == request) {
            return;
        }
        // 获取请求中的methodUrl参数
        String methodUrl = request.getParameter("methodUrl");
        try {
            // 获取执行的方法
            Method method = JoinPointUtil.getMethodByJoinPoint(joinPoint);
            // 获取方法上指定的注解
            ReturnMore annotation = method.getAnnotation(ReturnMore.class);
            // 获取方法上指定注解的memberValues
            Map<String, Object> memberValues = JoinPointUtil.getAnnotationValuesFromMethod(method, annotation);
            // 如果methodUrl参数不为空，则表示需要返回json数据，所以将方法上的注解的needjson属性设置为true
            memberValues.put("needJson", methodUrl != null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @After("myPointCut()")
    public void retrunMore(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        Model model = null;
        for(Object param : args){
            if(param instanceof HttpServletRequest){
                request = (HttpServletRequest) param;
            }
            if(param instanceof HttpServletResponse){
                response = (HttpServletResponse) param;
            }
            if(param instanceof Model){
                model = (Model) param;
            }
        }
        // 如果没有request或者没有response，则直接返回
        if(null == request && null == response){
            return;
        }
        String methodUrl = request.getParameter("methodUrl");
        // 如果有methodUrl，则返回json数据
        if(null != methodUrl){
            Map<String, Object> map = model.asMap();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // 获取流，已流的形式返回json数据
            try(PrintWriter pw = response.getWriter()) {
                pw.write(JSON.toJSONString(Result.succ(200, "获取成功", map)));
                pw.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
