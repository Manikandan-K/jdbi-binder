package com.github.rkmk.binder;

import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.github.rkmk.binder.FieldHelper.get;
import static com.github.rkmk.binder.FieldHelper.getFields;


public class BindObjectFactory implements BinderFactory {

    public Binder build(Annotation annotation) {

        return new Binder<BindObject, Object>() {
            @Override
            public void bind(SQLStatement<?> q, BindObject bind, Object arg) {
                bindObject(q, "", bind.value(), arg);
            }

            private void bindObject(SQLStatement<?> q, String parentNameSpace, String currentNameSpace, Object arg) {
                String prefix = parentNameSpace + (currentNameSpace.equals("__bind_object__") ? "" : currentNameSpace + ".");

                for (Field field : getFields(arg)) {
                    Object fieldValue = get(field, arg);
                    if (field.isAnnotationPresent(BindObject.class)) {
                        bindObject(q, prefix, field.getAnnotation(BindObject.class).value(), fieldValue);
                    } else {
                        q.dynamicBind(field.getType(), prefix + field.getName(), fieldValue);
                    }
                }
            }

        };
    }
}
