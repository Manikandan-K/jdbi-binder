package com.github.rkmk.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static java.util.Objects.nonNull;

public class Property implements IProperty{

    private final Field field;
    private final Method readMethod;
    private String name;

    public Property(Field field, Method readMethod, String name) {
        this.field = field;
        this.readMethod = readMethod;
        this.name = name;
    }

    @Override
    public Object getValue(Object obj) {
        try {
            return readMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Problem in accessing getter method for "+readMethod.getName());
        }

    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
        return nonNull(field) && field.isAnnotationPresent(clazz);
    }

    @Override
    public Class<?> getType() {
        return readMethod.getReturnType();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameSpace() {
        String value = field.getAnnotation(BindObject.class).value();
        return value.equals("__bind_object__") ? "" : value + ".";

    }

}
