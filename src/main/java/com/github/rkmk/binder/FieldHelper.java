package com.github.rkmk.binder;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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


    public static Collection<IProperty> getProperties(Class<?> type) {
        Map<String, IProperty> properties = new HashMap<>();

        populateFieldProperties(type, properties);
        populateMethodProperties(type, properties);

        return properties.values();
    }

    private static void populateMethodProperties(Class<?> type, Map<String, IProperty> properties) {
        PropertyDescriptor[] props = getPropertyDescriptors(type);
        for (PropertyDescriptor prop : props) {
            Method readMethod = prop.getReadMethod();
            if (nonNull(readMethod) && shouldUseGetter(readMethod)) {
                properties.put(prop.getName(), new MethodProperty(readMethod, prop.getName()));
            }
        }
    }

    private static void populateFieldProperties(Class<?> type, Map<String, IProperty> properties) {
        Class<?> clazz = type;
        while (clazz.getSuperclass() != null) {
            for (Field field : clazz.getDeclaredFields()) {
                properties.put(field.getName(), new FieldProperty(field));
            }
            clazz = clazz.getSuperclass();
        }
    }

    private static PropertyDescriptor[] getPropertyDescriptors(Class<?> type) {
        try {
            return Introspector.getBeanInfo(type).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            String message = String.format("unable to access bean properties for %s", type);
            throw new IllegalStateException(message, e);
        }
    }

    private static boolean shouldUseGetter(Method readMethod) {
        return (readMethod.isAnnotationPresent(Property.class) || readMethod.isAnnotationPresent(BindObject.class));
    }
}
