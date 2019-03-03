package com.example.core.domain.dto;

import com.example.core.domain.entity.FieldType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;

public class FormFieldTypeTest {


    @Test
    public void isAssignableWorksForBaseTypes() {
        FieldType boolType = FieldType.BOOLEAN;
        assertTrue(boolType.isAssignable(Boolean.class));

        FieldType enumType = FieldType.ENUMERATED;
        assertTrue(enumType.isAssignable(Enum.class));

        FieldType dateType = FieldType.DATE;
        assertTrue(dateType.isAssignable(Date.class));
        assertTrue(dateType.isAssignable(Temporal.class));

        FieldType integralNumberType = FieldType.INTEGRAL_NUMBER;
        assertTrue(integralNumberType.isAssignable(int.class));
        assertTrue(integralNumberType.isAssignable(Integer.class));
        assertTrue(integralNumberType.isAssignable(BigInteger.class));

        FieldType decimalType = FieldType.DECIMAL_NUMBER;
        assertTrue(decimalType.isAssignable(BigDecimal.class));
        assertTrue(decimalType.isAssignable(double.class));
        assertTrue(decimalType.isAssignable(float.class));
        assertTrue(decimalType.isAssignable(Double.class));
        assertTrue(decimalType.isAssignable(Float.class));

        FieldType textType = FieldType.TEXT;
        assertTrue(textType.isAssignable("any string!".getClass()));

    }

    @Test
    public void isAssignableWorksForSubclassesOfBaseTypes(){
        FieldType enumType = FieldType.ENUMERATED;
        assertTrue(enumType.isAssignable(FieldType.BOOLEAN.getClass()));
        assertTrue(enumType.isAssignable(DayOfWeek.FRIDAY.getClass()));

        FieldType dateType = FieldType.DATE;
        assertTrue(dateType.isAssignable(Instant.class));
    }
}