package cn.pengan.aspects;

import cn.pengan.annotations.DataOperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class AOPDataOperationLog {

    private final static Logger logger = LoggerFactory.getLogger(AOPDataOperationLog.class);

    @Around("@annotation(dataOperationLog)")
    public Object aroundLogger(ProceedingJoinPoint proceedingJoinPoint, DataOperationLog dataOperationLog) {
        Object result = null;
        String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String value = dataOperationLog.value();
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            //before
            result = proceedingJoinPoint.proceed(args);
            //after
        } catch (Throwable throwable) {
            // throwing
            logger.error("【{}】：【{}】", declaringTypeName, throwable.getMessage());
            throwable.printStackTrace();
        } finally {
            //returning
        }
        logger.debug("【{}】：【{}】", declaringTypeName, value);
        return result;
    }
}
