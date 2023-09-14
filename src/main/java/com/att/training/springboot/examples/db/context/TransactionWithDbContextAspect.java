package com.att.training.springboot.examples.db.context;

import com.att.training.springboot.examples.db.DbContext;
import com.att.training.springboot.examples.db.DbRegion;
import com.att.training.springboot.examples.user.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
@Slf4j
public class TransactionWithDbContextAspect {
    @Pointcut("within(com.att.training.springboot.examples..*)")
    void inApp() {}

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    void transactionalMethod() {}

    @Pointcut("@annotation(com.att.training.springboot.examples.db.context.WithDbContext)")
    void withDbContextMethod() {}

    @Pointcut("args(user,..)")
    void hasUserArg(User user) {}

    @Around("inApp() && transactionalMethod() && hasUserArg(user)")
    public Object adviseTransactionalMethod(ProceedingJoinPoint joinPoint, User user) {
        return setDbContext(joinPoint, user);
    }

    @Around("inApp() && withDbContextMethod() && hasUserArg(user)")
    public Object adviseDbContextMethod(ProceedingJoinPoint joinPoint, User user) {
        return setDbContext(joinPoint, user);
    }

    @SneakyThrows
    private Object setDbContext(ProceedingJoinPoint joinPoint, User user) {
        var dbRegion = DbRegion.fromId(user.id());
        var methodSignature = joinPoint.getSignature().toShortString();
        log.info("#{} - user = {}, dbRegion = {}", methodSignature, user, dbRegion);
        DbContext.setRegion(dbRegion);
        try {
            return joinPoint.proceed();
        } finally {
            DbContext.clear();
            log.info("#{} - cleared db context", methodSignature);
        }
    }
}

