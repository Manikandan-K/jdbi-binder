package com.github.rkmk.binder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldHelper {

    public static Object get(Field field, Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("The field %s is not accessible",field.getName()), e);
        }
    }

    public static List<Field> getFields(Class<?> type) {
        List<Field> result = new ArrayList<>();
        Class<?> clazz = type;
        while(clazz.getSuperclass() != null) {
            result.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return result;
    }

}
