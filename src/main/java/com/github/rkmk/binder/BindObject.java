package com.github.rkmk.binder;

import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@BindingAnnotation(BindObjectFactory.class)
@Documented
public @interface BindObject {
    String value() default "__bind_object__";
}
