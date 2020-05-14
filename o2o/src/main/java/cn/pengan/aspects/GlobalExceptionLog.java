package cn.pengan.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class GlobalExceptionLog {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionLog.class);

    @AfterThrowing(value = "execution(* cn.pengan.*.*.*.*(..))",throwing = "ex")
    public void recordException(JoinPoint joinPoint,Exception ex) {
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        logger.error("【{}】： {}",declaringTypeName,ex.getMessage());
    }
}
