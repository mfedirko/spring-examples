package com.example.core.domain.mapper;

import com.example.core.annotation.IEntityField;
import com.example.core.domain.IEntity;
import com.example.core.domain.entity.FieldType;

public class TestEntity implements IEntity{

    @IEntityField(label = "Field Type", fieldType = FieldType.ENUMERATED)
    private FieldType fieldType;

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String entityName() {
        return "test";
    }
}
