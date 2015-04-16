package com.github.rkmk.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodProperty implements IProperty {

    private Method method;
    private String name;

    public MethodProperty(Method method, String name) {
        this.method = method;
        this.name = name;
    }

    @Override
    public Object getValue(Object obj) {
        try {
            return method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Problem in accessing getter method for "+method.getName());
        }
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
        return method.isAnnotationPresent(clazz);
    }

    @Override
    public Class<?> getType() {
        return method.getReturnType();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameSpace() {
        String value = method.getAnnotation(BindObject.class).value();
        return value.equals("__bind_object__") ? "" : value + ".";
    }
}
