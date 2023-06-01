package com.att.training.springboot.examples.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class TimedAspect {
    @Pointcut("within(com.att.training.springboot.examples..*)")
    void inApp() {}

    @Pointcut("@within(Timed) && !@annotation(Timed)")
    void timedClass() {}

    @Pointcut("@annotation(Timed)")
    void timedMethod() {}

    @Around("inApp() && timedClass()")
    public void adviseMethodsOfAnnotatedClass(ProceedingJoinPoint joinPoint) {
        timeMethodExecution(joinPoint);
    }

    @Around("inApp() && timedMethod()")
    public void adviseAnnotatedMethods(ProceedingJoinPoint joinPoint) {
        timeMethodExecution(joinPoint);
    }

    @SneakyThrows
    private void timeMethodExecution(ProceedingJoinPoint joinPoint) {
        log.info("#timed - Before {}", joinPoint.getSignature().toShortString());
        var stopWatch = new StopWatch();
        stopWatch.start();
        joinPoint.proceed();
        stopWatch.stop();
        log.info("#timed - After {}, time: {}"
                , joinPoint.getSignature().toShortString()
                , stopWatch.getTotalTimeMillis());
    }
}
