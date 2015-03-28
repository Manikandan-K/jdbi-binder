package com.github.rkmk.binder;

import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@BindingAnnotation(BindObjectFactory.class)
public @interface BindObject {
    String value() default "__bind_object__";
}
