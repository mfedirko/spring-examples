package com.example.core.annotation;

import com.example.core.domain.entity.FieldType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IEntityField {
    String label();
    FieldType fieldType();
    boolean nullable() default true;
}
