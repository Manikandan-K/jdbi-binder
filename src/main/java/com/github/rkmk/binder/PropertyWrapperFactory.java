package com.github.rkmk.binder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.rkmk.binder.FieldHelper.getProperties;

public class PropertyWrapperFactory {

    private static Map<Class<?>, HashMap<String, PropertyWrapper>> cachedFields = new HashMap<>();

    public static HashMap<String, PropertyWrapper> getFieldsFor(Class<?> clazz)  {
        if(!cachedFields.containsKey(clazz)) {
            HashMap<String, PropertyWrapper> propertyWrappers = new HashMap<>();
            processFields(clazz, "", new ArrayList<>(), propertyWrappers);
            cachedFields.put(clazz, propertyWrappers);
        }
        return cachedFields.get(clazz);
    }


    public static void processFields(Class<?> clazz, String nameSpace, List<IProperty> parentProperties, Map<String, PropertyWrapper> propertyWrappers) {
        for (IProperty property : getProperties(clazz)) {
            List<IProperty> properties = getCurrentProperties(parentProperties, property);
            if (property.isAnnotationPresent(BindObject.class)) {
                   processFields(property.getType(), nameSpace(nameSpace, property), properties,  propertyWrappers);
            } else {
                propertyWrappers.put(propertyName(nameSpace, property), new PropertyWrapper(properties, property));
            }
        }
    }

    private static List<IProperty> getCurrentProperties(List<IProperty> parentProperties, IProperty property) {
        List<IProperty> properties = new ArrayList<>(parentProperties);
        properties.add(property);
        return properties;
    }

    private static String propertyName(String nameSpace, IProperty property) {
        return nameSpace + property.getName();
    }

    private static String nameSpace(String parentObjectNameSpace, IProperty property) {
        return parentObjectNameSpace + property.getNameSpace()  ;
    }

}
