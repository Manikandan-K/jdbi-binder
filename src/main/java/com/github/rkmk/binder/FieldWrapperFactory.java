package com.github.rkmk.binder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.rkmk.binder.FieldHelper.getFields;

public class FieldWrapperFactory {

    private static Map<Class<?>, HashMap<String, FieldWrapper>> cachedFields = new HashMap<>();

    public static HashMap<String, FieldWrapper> getFieldsFor(Class<?> clazz) {
        if(!cachedFields.containsKey(clazz)) {
            HashMap<String, FieldWrapper> fieldWrappers = new HashMap<>();
            processFields(clazz, "", new ArrayList<>(), fieldWrappers);
            cachedFields.put(clazz, fieldWrappers);
        }
        return cachedFields.get(clazz);
    }


    public static void processFields(Class<?> clazz, String nameSpace, List<Field> parentFields, Map<String, FieldWrapper> fieldWrappers) {
        for (Field field : getFields(clazz)) {
            ArrayList<Field> fields = getCurrentFields(parentFields, field);
            if (field.isAnnotationPresent(BindObject.class)) {
                   processFields(field.getType(), nameSpace(nameSpace, field), fields,  fieldWrappers);
            } else {
                fieldWrappers.put(fieldName(nameSpace, field), new FieldWrapper(fields, field) );
            }
        }
    }

    private static ArrayList<Field> getCurrentFields(List<Field> parentFields, Field field) {
        ArrayList<Field> fields = new ArrayList<>(parentFields);
        fields.add(field);
        return fields;
    }

    private static String fieldName(String nameSpace, Field field) {
        return nameSpace + field.getName();
    }

    private static String nameSpace(String parentObjectNameSpace, Field field) {
        String currentNameSpace = field.getAnnotation(BindObject.class).value();
        return parentObjectNameSpace + (currentNameSpace.equals("__bind_object__") ? "" : currentNameSpace + ".");
    }


}
