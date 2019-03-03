package com.example.core.domain.dto;

import com.example.core.domain.entity.EntityField;
import com.example.core.domain.entity.FieldType;
import com.example.core.domain.mapper.TestEntity;
import java.time.DayOfWeek;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;

public class EntityFieldTest {


    @Test
    public void setsValueOnlyIfCorrectType() {
        EntityField field = new EntityField("creationTimestamp", FieldType.DATE,Object.class);
        field.setValue("");
        field.setValue(2);
        field.setValue(new Object());

        assertNull(field.getValue());

        Date currentTime = new Date();
        field.setValue(currentTime);
        assertEquals(field.getValue(),currentTime);

        EntityField field2 = new EntityField("firstName",FieldType.TEXT,Object.class);
        field2.setValue("a string name");
        assertEquals(field2.getValue(),"a string name");

        field2.setValue(new Date());
        assertEquals(field2.getValue(),"a string name");

        EntityField field3 = new EntityField("dayOfWeek", FieldType.ENUMERATED,Object.class);
        field3.setValue(new Object());
        field3.setValue(55);
        assertNull(field3.getValue());
        field3.setValue(DayOfWeek.FRIDAY);
        assertEquals(field3.getValue(),DayOfWeek.FRIDAY);

        EntityField field4 = new EntityField("amount",FieldType.DECIMAL_NUMBER,Object.class);
        field4.setValue("55.2424");
        assertNull(field4.getValue());
        field4.setValue(55.44);
        assertEquals(55.44,field4.getValue());

        EntityField field5 = new EntityField("integer",FieldType.INTEGRAL_NUMBER,Object.class);
        field5.setValue("55");
        field5.setValue(55.44);
        assertNull(field5.getValue());
        field5.setValue(23);
        assertEquals(23,field5.getValue());

    }

    @Test
    public void setsNullValueIfCorrectNullability(){
        EntityField nullable = new EntityField("total",FieldType.DECIMAL_NUMBER,Object.class);
        nullable.setValue(25.3);
        assertNotNull(nullable.getValue());
        nullable.setNullable(false);
        nullable.setValue(null);
        assertNotNull(nullable.getValue());

        nullable.setNullable(true);
        nullable.setValue(null);
        assertNull(nullable.getValue());
    }

    @Test
    public void doesNotSetNullableFalseIfValueNull(){
        EntityField initialNull = new EntityField("",FieldType.ENUMERATED,Object.class);
        initialNull.setNullable(false);
        assertTrue(initialNull.isNullable());
    }

    @Test
    public void maskedValueWorksForAllFieldTypes(){
        EntityField string = new EntityField("aField",FieldType.TEXT,Object.class);
        string.setValue("somestring");
        assertEquals(string.getValue(),"somestring");

        string.setMasked(true);
        assertTrue(string.isMasked());
        assertEquals("**********",string.getValue());
        assertEquals("somestring",string.getUnmaskedValue());
        string.setMasked(false);
        assertEquals("somestring",string.getValue());

        EntityField integer = new EntityField("integer",FieldType.INTEGRAL_NUMBER,Object.class);
        integer.setValue(42424);
        assertEquals(integer.getValue(),42424);
        integer.setMasked(true);
        assertEquals(integer.getValue(),99999999);
        integer.setMasked(false);
        assertEquals(integer.getValue(),42424);

        EntityField decimal = new EntityField("decimal",FieldType.DECIMAL_NUMBER,Object.class);
        decimal.setValue(3434.232);
        assertEquals(decimal.getValue(),3434.232);
        decimal.setMasked(true);
        assertEquals(999999.99,decimal.getValue());
        assertEquals(3434.232,decimal.getUnmaskedValue());

        EntityField other = new EntityField("other",FieldType.ENUMERATED,Object.class);
        other.setValue(DayOfWeek.SATURDAY);
        assertEquals(other.getValue(),DayOfWeek.SATURDAY);
        other.setMasked(true);
        assertNull(other.getValue());
        assertEquals(other.getUnmaskedValue(),DayOfWeek.SATURDAY);

    }

    @Test
    public void valueListWorksCorrectlyForEnumAndNonEnum(){
        // for enumerated fieldtypes, value list should return the values()
        EntityField enumerated = new EntityField("An Enum",FieldType.ENUMERATED,FieldType.class);
        assertArrayEquals(enumerated.getValueList(),FieldType.values());
        enumerated.setValue(FieldType.INTEGRAL_NUMBER);
        assertArrayEquals(enumerated.getValueList(),FieldType.values());

        //for non-enumerated fieldtypes, value list should return a singleton array of the value
        EntityField text = new EntityField("Text value",FieldType.TEXT,Object.class);
        text.setValue("the value");
        assertArrayEquals(text.getValueList(),new String[]{"the value"});

    }


}