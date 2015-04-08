package com.github.rkmk.binder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class FieldHelper {

    public static Object get(Field field, Object object) {
        if (isNull(object))
            return null;
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("The field %s is not accessible", field.getName()), e);
        }
    }


    public static List<IProperty> getProperties(Class<?> type) {
        List<IProperty> result = new ArrayList<>();
        Class<?> clazz = type;
        while (clazz.getSuperclass() != null) {
            List<IProperty> properties = asList(clazz.getDeclaredFields()).stream().map(FieldProperty::new).collect(toList());
            result.addAll(properties);
            clazz = clazz.getSuperclass();
        }
        return result;
    }
}
