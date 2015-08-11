/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.job.lr.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
/**
 * 声明一个切面
 * 
 * 记录日志的切面
 * 
 * @author liuy
 *
 */
@Aspect
@Component
//@EnableAspectJAutoProxy
public class LogAspect {
 
    /**
     * 切入点：表示在哪个类的哪个方法进行切入。配置有切入点表达式
     * 
     * 	匹配com.fycoder.ll.web包及其子包中所有类中的所有方法，返回类型任意，方法参数任意
     */
    @Pointcut("execution(* com.job.lr.web..*(..))")
    //@Pointcut("execution(public * * (..))")
    public void pointcutExpression() {
         
    }
     
    /**
     * 1 前置通知
     * @param joinPoint
     */
    @Before("pointcutExpression()")
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("前置通知执行了");
    }
     
    /**
     * 2 后置通知
     */
    @After("pointcutExpression()") // 在方法执行之后执行的代码. 无论该方法是否出现异常
    public void afterMethod(JoinPoint joinPoint) {
        System.out.println("后置通知执行了，有异常也会执行");
    }
     
    /**
     * 3 返回通知
     * 
     * 在方法法正常结束受执行的代码
     * 返回通知是可以访问到方法的返回值的!
     * 
     * @param joinPoint
     * @param returnValue
     * 
     */
    @AfterReturning(value = "pointcutExpression()", returning = "returnValue")
    public void afterRunningMethod(JoinPoint joinPoint, Object returnValue) {
        System.out.println("返回通知执行，执行结果：" + returnValue);
    }
     
    /**
     * 4 异常通知
     * 
     * 在目标方法出现异常时会执行的代码.
     * 可以访问到异常对象; 且可以指定在出现特定异常时在执行通知代码
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointcutExpression()", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception e){
        System.out.println("异常通知, 出现异常 ：" + e);
    }
     
    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数. 
     * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.
     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     */
     
    @Around("pointcutExpression()")
    public Object aroundMethod(ProceedingJoinPoint pjd){
         
        Object result = null;
        String methodName = pjd.getSignature().getName();
         
        try {
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            //执行目标方法
            result = pjd.proceed();
            //返回通知
            System.out.println("The method " + methodName + " ends with " + result);
        } catch (Throwable e) {
            //异常通知
            System.out.println("The method " + methodName + " occurs exception:" + e);
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println("The method " + methodName + " ends");
         
        return result;
    }
     
     
}