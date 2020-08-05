package hfuu.examination.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*  
* 自定义注解 拦截service 
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    String description() default "";// 描述
    
    String moduleType() default "";// 模块代码
    
    String operateValue() default "";// 操作类型
}
