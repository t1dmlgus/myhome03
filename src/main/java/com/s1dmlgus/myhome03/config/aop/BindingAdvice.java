package com.s1dmlgus.myhome03.config.aop;


import com.s1dmlgus.myhome03.web.dto.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


// configure(설정) - controller - component

@Log4j2
@Component
@Aspect
public class BindingAdvice {

    @Around("execution(* com.s1dmlgus.myhome03..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();


        System.out.println("AOP-type = " + type);               // 호출(리플랙션)된 Controller
        System.out.println("AOP-method = " + method);           // Controller 안에 메서드


        Object[] args = proceedingJoinPoint.getArgs();      // 호출된 메서드 매개변수


        for (Object arg : args) {

            if (arg instanceof BindingResult){

                BindingResult bindingResult = (BindingResult) arg;


                /* 공통기능 */
                if (bindingResult.hasErrors()) {

                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());

                        log.warn(type + "." + method + "() -> 필드: " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                        //log.debug(type + "." + method + "() -> 필드: " + error.getField() + ", 메시지 : " + error.getDefaultMessage());

                    }


                    return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }


            }
        }
        
        return proceedingJoinPoint.proceed();       // 함수의 스택을 실행하라

    }


}
