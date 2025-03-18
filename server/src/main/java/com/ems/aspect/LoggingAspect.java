package com.ems.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.ems.controllers..*(..)) || execution(* com.ems.services..*(..))")

    public Object logExecutionDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        log.info("Executing method: {}", methodName);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.info("Method Arguments: {}", (Object) args);
        }

        Instant start = Instant.now();
        try {
            Object result = joinPoint.proceed();
            Instant end = Instant.now();
            log.info("Method {} executed successfully in {} ms", methodName, Duration.between(start, end).toMillis());
            return result;
        } catch (Exception e) {
            log.error("Method {} failed: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }
}
