package com.tan.testreturn.aspectj;


import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAOP {

    @Pointcut("@annotation(com.tan.testreturn.annotation.ReturnMore)")
    public void myPointCut(){}

    @Around("@annotation(com.tan.testreturn.annotation.ReturnMore)")
    public Object retSomething(){
        return null;
    }

}
