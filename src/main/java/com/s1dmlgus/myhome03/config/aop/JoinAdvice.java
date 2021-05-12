package com.s1dmlgus.myhome03.config.aop;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@Aspect
public class JoinAdvice {


//    @Around("execution(* com.s1dmlgus.myhome03..UserApiController.save(..))")
//    public void validId(ProceedingJoinPoint proceedingJoinPoint) {
//
//
//        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
//        String method = proceedingJoinPoint.getSignature().getName();
//
//        log.info("AOP-Type = " + type);
//        log.info("AOP-method = "+ method);
//
//
//    }

}
