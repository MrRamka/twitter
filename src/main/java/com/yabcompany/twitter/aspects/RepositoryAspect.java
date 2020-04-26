package com.yabcompany.twitter.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RepositoryAspect {

    /**
     * Aspect for logging execution(* PACKAGE.*.*(..))
     *
     * @param joinPoint
     * @see JoinPoint
     */
    @Before(value = "execution(* com.yabcompany.twitter.repositories.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("Request to data base " + joinPoint);
    }

    /**
     * Aspect for logging exceptions in execution(* PACKAGE.*.*(..))
     *
     * @param joinPoint
     * @param error
     * @see JoinPoint
     * @see Throwable
     */
    @AfterThrowing(pointcut = "execution(* com.yabcompany.twitter.repositories.*.*(..))", throwing = "error")
    public void afterThrowing(JoinPoint joinPoint, Throwable error) {
        log.info(joinPoint + " " + error);
    }


}
