package cn.pengan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class GlobalExceptionLog {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionLog.class);

    @AfterThrowing(value = "execution(* cn.pengan.*.*.*.*(..))", throwing = "ex")
    public void recordException(JoinPoint point, Exception ex) {
        String declaringTypeName = point.getSignature().getDeclaringTypeName();
        logger.error("【{}】： {}", declaringTypeName, ex.getMessage());
    }
}
