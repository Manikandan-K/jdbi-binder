package com.github.rkmk.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.github.rkmk.binder.FieldHelper.get;

public class FieldProperty implements IProperty {

    private Field field;

    public FieldProperty(Field field) {
        this.field = field;
    }

    @Override
    public Object getValue(Object obj) {
        return get(field, obj);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public String getNameSpace() {
        String value = field.getAnnotation(BindObject.class).value();
        return value.equals("__bind_object__") ? "" : value + ".";
    }
}
