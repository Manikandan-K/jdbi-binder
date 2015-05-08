package com.github.rkmk.binder;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class FieldHelper {

    private static Field getField(Class clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null ;
        }
    }


    public static Collection<IProperty> getProperties(Class<?> type) {
        Map<String, IProperty> properties = new HashMap<>();
        PropertyDescriptor[] props = getPropertyDescriptors(type);
        for (PropertyDescriptor prop : props) {
            Method readMethod = prop.getReadMethod();
            if (nonNull(readMethod)) {
                String fieldName = prop.getName();
                properties.put(fieldName, new Property(getField(type, fieldName), readMethod, fieldName));
            }
        }
        return properties.values();
    }

    private static PropertyDescriptor[] getPropertyDescriptors(Class<?> type) {
        try {
            return Introspector.getBeanInfo(type).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            String message = String.format("unable to access bean properties for %s", type);
            throw new IllegalStateException(message, e);
        }
    }

}
