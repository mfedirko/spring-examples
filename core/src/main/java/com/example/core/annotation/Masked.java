package com.example.core.annotation;

import com.example.core.util.masking.MaskingStrategy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Masked {
    MaskingStrategy strategy() default MaskingStrategy.MASKED;
     String[] exceptRoles() default {};
    String[] exceptModules() default {};
}
