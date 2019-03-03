package com.example.core.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.validation.Validator;

public class Entity  implements Serializable {



    protected String name;
    protected final List<EntityField> fields = new ArrayList<>();

    protected Map<String,String[]> fieldValidationErrors = new HashMap<>();
    protected String[] globalValidationErrors;
    protected Map<String,Validator> fieldValidators = new HashMap<>();
    protected Set<Validator> globalValidators = new HashSet<>();

    protected Map<String,EntityField> fieldNameMap = new HashMap<>();


    public void setName(String name) {
        this.name = name;
    }

    public boolean hasValidationErrors(){
        boolean hasFieldErrors =  fieldValidationErrors != null && !fieldValidationErrors.isEmpty();
        boolean hasGlobalErrors = globalValidationErrors != null && globalValidationErrors.length > 0;
        return hasFieldErrors || hasGlobalErrors;
    }

    public void addField(EntityField field){
        if (field == null) return;
        if (fieldNameMap.get(field.getName()) != null) return;
        fields.add(field);
        fieldNameMap.put(field.getName(),field);
    }

    public void addFieldValidationError(String error,String fieldName){
        if (error == null) return;
        if (fieldValidationErrors == null) fieldValidationErrors = new HashMap<>();
        boolean fieldExistsWithName = fieldNameMap.containsKey(fieldName);
        if (fieldExistsWithName){
            String[] existingErrors = fieldValidationErrors.get(fieldName);
            if (existingErrors == null) {
                fieldValidationErrors.put(fieldName, new String[]{error});
            }
            else {
                String[] newErrors = appendToArray(existingErrors,error);
                fieldValidationErrors.put(fieldName,newErrors);
            }
        }
    }
    protected <T> T[] appendToArray(T[] array,T next){
        if (array == null) return null;
        T[] newArray = Arrays.copyOf(array,array.length + 1);
        newArray[newArray.length - 1] = next;
        return newArray;
    }

    public void addGlobalValidationError(String error){
        if (error == null) return;
        if (globalValidationErrors == null) globalValidationErrors = new String[]{error};
        else {
            globalValidationErrors = appendToArray(globalValidationErrors,error);
        }
    }



    public String getName() {
        return name;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public EntityField getField(String name){
        return fieldNameMap.get(name);
    }

    public String[] getFieldValidationError(String fieldName) {
        return fieldValidationErrors.get(fieldName);
    }

    public String[] getAllValidationErrors(){
        String[] fieldErrs = fieldValidationErrors.entrySet().stream()
                .flatMap( e -> Arrays.asList(e.getValue()).stream())
                .collect(Collectors.toList()).toArray(new String[0]);
        String[] globalErrors = globalValidationErrors;
        int totalLength = globalErrors != null ? globalErrors.length + fieldErrs.length : fieldErrs.length;
        String[] allErrs = Arrays.copyOf(fieldErrs,totalLength);
        if (allErrs.length > fieldErrs.length){
            int index = fieldErrs.length;
            for (String globalErr : globalErrors){
                allErrs[index++] = globalErr;
            }
        }
        return allErrs;
    }

    public String[] getGlobalValidationErrors() {
        return globalValidationErrors;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                ", fieldValidationErrors=" + fieldValidationErrors +
                ", globalValidationErrors=" + Arrays.toString(globalValidationErrors) +
                '}';
    }
}
