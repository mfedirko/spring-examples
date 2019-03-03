package com.example.core.service;

import com.example.core.annotation.IEntityField;
import com.example.core.annotation.Masked;
import com.example.core.domain.IEntity;
import com.example.core.domain.entity.Entity;
import com.example.core.domain.entity.EntityField;
import com.example.core.domain.entity.FieldType;
import com.example.core.util.masking.MaskingUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

/*
* Maps a single source object to an Entity object and vice-versa.
* Source object must implement IEntity interface and the source object's fields
* should be annotated with @IEntityField.
*/
@Component
public class SingleSourceEntityMapper {

    // only supports entity with all fields having a single source
    public Object mapToSource(Entity entity) throws Exception {
        Object source = null;
        if (entity == null) return null;
        for (EntityField field : entity.getFields()){
            if (source == null){
                source = field.getSourceClass().newInstance();
            }
            String fieldName = field.getName();
            PropertyUtils.setProperty(source,fieldName,field.getValue());
        }
        return source;
    }

    public Entity mapToEntity(IEntity source) throws Exception {
        if (source == null) return null;
        Entity target = new Entity();
        Class sourceClass = source.getClass();
        target.setName(source.entityName());
        for (Field field : sourceClass.getDeclaredFields() ){
            IEntityField iEntityField = field.getAnnotation(IEntityField.class);
            if (iEntityField != null) {
                EntityField entityField = new EntityField(field.getName(),iEntityField.fieldType(),field.getType());
                entityField.setLabel(iEntityField.label());
                field.setAccessible(true);
                entityField.setValue(field.get(source));

                NotNull notNull = field.getAnnotation(NotNull.class);
                boolean notNullable = !iEntityField.nullable() || notNull != null;
                if (notNullable) {
                    entityField.setNullable(false);
                }
                Masked masked = field.getAnnotation(Masked.class);
                if (masked != null) {
                    processMasking(entityField, masked);
                }
                target.addField(entityField);
            }
        }
        return target;
    }






    protected void processMasking(EntityField field, Masked maskedAnnotation){
        MaskingUtil.processMasking(field,maskedAnnotation);
    }
}
