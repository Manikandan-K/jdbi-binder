package com.github.rkmk.binder;

import java.util.List;

public class PropertyWrapper {

    private List<IProperty> properties;
    private Class<?> type;

    public PropertyWrapper(List<IProperty> properties, IProperty currentProperty) {
        this.properties = properties;
        this.type = currentProperty.getType();
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue(Object object) {
        Object value = object;
        for (IProperty property : properties) {
            value = property.getValue(value);
        }
        return value;
    }
}
