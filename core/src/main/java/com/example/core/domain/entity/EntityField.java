package com.example.core.domain.entity;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.util.Assert;

public class EntityField {
    private String name;
    private FieldType fieldType;
    private String label;

    protected Class<?> sourceClass;

    protected Class<?> type;

    private Object value;
    private boolean isNullable = true;
    private boolean isVisible = true;
    private boolean masked = false;

    public EntityField(){
        this.type = null;
        this.name = null;
        this.fieldType = null;
    }

    public EntityField(String name, FieldType formType, Class<?> type){
        Assert.notNull(name,"Field name must not be null");
        Assert.notNull(formType,"Form field type must not be null");
        Assert.notNull(type,"Type cannot be null");
        this.name = name;
        this.fieldType = formType;
        this.type = type;
    }

    private Object[] valueList;

    public Object[] getValueList() {
        if (valueList == null) {
            if (fieldType != FieldType.ENUMERATED) {
                valueList = Collections.singletonList(value).toArray();
            } else {
                try {
                    this.valueList = collectAllEnumValues(type);
                } catch (Exception e) {
                    System.err.println("Failed to extract value list of enum");
                    valueList = Collections.singletonList(value).toArray();
                }
            }
        }
        return valueList;
    }

    protected static <T extends Enum<T>> T[] collectAllEnumValues(Class<?> clazz) throws Exception {
        Method method = clazz.getDeclaredMethod("values");
        Object obj = method.invoke(null);
        return (T[]) obj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setValue(Object value){
        System.out.println(String.format("Attempting to assign %s",value ));
        if (value == null ){
            if (isNullable) {
                this.value = null;
            }
        }
        else {

            if (fieldType.isAssignable(value.getClass())) {
                this.value = value;
                this.type = value.getClass();
            }
            else {
                if (value instanceof  String){

                }

            }

       }
    }

    protected void valueFromString(String string){
        switch (this.fieldType){
            case ENUMERATED:
        }
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public Class<?> getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public String getName() {
        return name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }
    public Object getUnmaskedValue(){
        return value;
    }

    protected Object getMaskedValue(){
        if (value != null){
            switch (fieldType){
                case TEXT:
                    int length = ((String) value).length();
                    if (length < 10)
                        return String.join("", Collections.nCopies(((String)value).length(),"*"));
                    else return ((String) value).charAt(0) + String.join("", Collections.nCopies(length - 2,"*")) + ((String) value).charAt(length -1);
                case DECIMAL_NUMBER:
                    return 999999.99;
                case INTEGRAL_NUMBER:
                    return  99999999;
                default:
                    return null;
            }
        }
        else {
            return null;
        }
    }

    public Object getValue() {
        if (masked){
            return getMaskedValue();
        }else {
            return getUnmaskedValue();
        }
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isMasked() {
        return masked;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
    }

    public void setNullable(boolean nullable) {
        if (getValue() != null) {
            isNullable = nullable;
        }
        else {
            isNullable = true;
        }
    }



    public void setVisible(boolean visible) {
        isVisible = visible;
    }


    @Override
    public String toString() {
        return "EntityField{" +
                "name='" + name + '\'' +
                ", fieldType=" + fieldType +
                ", label='" + label + '\'' +
                ", sourceClass=" + sourceClass +
                ", type=" + type +
                ", value=" + value +
                ", isNullable=" + isNullable +
                ", isVisible=" + isVisible +
                ", masked=" + masked +
                ", valueList=" + Arrays.toString(valueList) +
                '}';
    }
}
