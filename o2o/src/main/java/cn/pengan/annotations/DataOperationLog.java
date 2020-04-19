package cn.pengan.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataOperationLog {

    @AliasFor("operationStatement")
    String value() default "";

    /**
     * 操作说明
     * @return
     */
    @AliasFor("value")
    String operationStatement() default "";
}
