package com.github.rkmk.binder;

import java.lang.annotation.Annotation;

public interface IProperty {
    public Object getValue(Object obj);

    boolean isAnnotationPresent(Class<? extends Annotation> clazz);

    Class<?> getType();

    String getName();

    String getNameSpace();
}
