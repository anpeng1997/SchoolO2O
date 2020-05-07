package cn.pengan.aspects;

import cn.pengan.annotations.DataOperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class DataOperationLogAOP {

    private final static Logger logger = LoggerFactory.getLogger(DataOperationLogAOP.class);

    /**
     * 记录操作日志
     * 声明该方法是一个返回通知:在目标方法正常结束之后返回(目标方法执行出现异常时不再执行)
     * 返回通知可以访问目标方法的执行结果
     * @param joinPoint
     * @param dataOperationLog
     * @param result
     */
    @AfterReturning(value = "@annotation(dataOperationLog)",returning = "result")
    public void afterReturningLog(JoinPoint joinPoint,DataOperationLog dataOperationLog,Object result){
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String value = dataOperationLog.value();
        logger.debug("【{}】：【{}】,【{}】", declaringTypeName, value,result);
    }

//    @Around("@annotation(dataOperationLog)")
//    public Object aroundLogger(ProceedingJoinPoint proceedingJoinPoint, DataOperationLog dataOperationLog) {
//        Object result = null;
//        String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
//        String value = dataOperationLog.value();
//        try {
//            Object[] args = proceedingJoinPoint.getArgs();
//            //before
//            result = proceedingJoinPoint.proceed(args);
//            //AfterReturning
//        } catch (Throwable throwable) {
//            // throwing
//            logger.error("【{}】：【{}】", declaringTypeName, throwable.getMessage());
//            throwable.printStackTrace();
//        } finally {
//            //After
//        }
//        logger.debug("【{}】：【{}】", declaringTypeName, value);
//        return result;
//    }
}
