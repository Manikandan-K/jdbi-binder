package com.github.rkmk.binder;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.github.rkmk.binder.FieldWrapperFactory.getFieldsFor;
import static java.util.Objects.isNull;


public class BindObjectFactory implements BinderFactory {

    public Binder build(Annotation annotation) {

        return new Binder<BindObject, Object>() {
            @Override
            public void bind(SQLStatement<?> q, BindObject bind, Object arg) {
                if(isNull(arg))
                    throw new RuntimeException("Object value is null");
                for (Map.Entry<String, FieldWrapper> fieldWrapperEntry : getFieldsFor(arg.getClass()).entrySet()) {
                    String nameSpace = fieldWrapperEntry.getKey();
                    FieldWrapper fieldWrapper = fieldWrapperEntry.getValue();
                    q.dynamicBind(fieldWrapper.getType(), nameSpace, fieldWrapper.getValue(arg));
                }
            }

        };
    }
}
