package com.github.rkmk.binder;

import java.lang.reflect.Field;
import java.util.List;

import static com.github.rkmk.binder.FieldHelper.get;

public class FieldWrapper {

    private List<Field> fields;
    private Class<?> type;

    public FieldWrapper(List<Field> fields, Field currentField) {
        this.fields = fields;
        this.type = currentField.getType();
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue(Object object) {
        Object value = object;
        for (Field field : fields) {
            value = get(field, value);
        }
        return value;
    }
}
