package com.example.core.domain.dto;

import com.example.core.domain.entity.Entity;
import com.example.core.domain.entity.EntityField;
import com.example.core.domain.entity.FieldType;
import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class EntityTest {
    private Entity entity;

    @Before
    public void setup(){
        entity = new Entity();
    }

    @Test
    public void addFieldPersistsFieldUnlessNull(){
        EntityField creationTimeField = new EntityField("creationTime", FieldType.DATE,Object.class);
        Date currentTime = new Date();
        creationTimeField.setValue(currentTime);

        entity.addField(creationTimeField);
        assertEquals(entity.getFields().size(),1);
        assertTrue(entity.getFields().contains(creationTimeField));
        assertEquals(entity.getField("creationTime"),creationTimeField);

        entity.addField(null);
        assertEquals(entity.getFields().size(),1);
        assertTrue(entity.getFields().contains(creationTimeField));

        EntityField lastName = new EntityField("lastName",FieldType.DATE,Object.class);
        lastName.setValue("");
        entity.addField(lastName);
        assertEquals(entity.getFields().size(),2);
        assertTrue(entity.getFields().contains(creationTimeField));
        assertTrue(entity.getFields().contains(lastName));
    }

    @Test
    public void cannotAddMultipleSameFieldNamesToSameEntity(){
        EntityField creationTimeField = new EntityField("creationTime",FieldType.DATE,Object.class);
        Date currentTime = new Date();
        creationTimeField.setValue(currentTime);

        entity.addField(creationTimeField);
        entity.addField(new EntityField("creationTime",FieldType.DATE,Object.class));
        assertEquals(entity.getFields().size(),1);
        assertTrue(entity.getFields().contains(creationTimeField));
        assertEquals(entity.getField("creationTime"),creationTimeField);
    }

    @Test
    public void addGlobalValidationErrorWorks(){
        assertFalse(entity.hasValidationErrors());

        entity.addGlobalValidationError("an error has occurred");
        assertArrayEquals(entity.getAllValidationErrors(), new String[]{"an error has occurred"});
        assertTrue(entity.hasValidationErrors());

        entity.addGlobalValidationError("another error");
        assertArrayEquals(entity.getAllValidationErrors(), new String[]{"an error has occurred","another error"});

        entity.addGlobalValidationError(null);
        assertArrayEquals(entity.getAllValidationErrors(), new String[]{"an error has occurred","another error"});
    }

    @Test
    public void addFieldValidationErrorAddedIfFieldExists(){
        entity.addFieldValidationError("an error","fieldName");
        assertFalse(entity.hasValidationErrors());

        EntityField creationTime = new EntityField("creationTimestamp",FieldType.DATE,Object.class);
        creationTime.setValue(new Date());
        entity.addField(creationTime);
        entity.addFieldValidationError("Date cannot be now","creationTimestamp");
        assertArrayEquals(new String[]{"Date cannot be now"},entity.getAllValidationErrors());
        entity.addFieldValidationError("another rror","creationTimestamp");
        assertArrayEquals(new String[]{"Date cannot be now","another rror"},entity.getAllValidationErrors());

        entity.addFieldValidationError(null,"creationTimestamp");
        entity.addFieldValidationError(null,null);
        entity.addFieldValidationError(null,"nonexistentFieldname");
        assertArrayEquals(new String[]{"Date cannot be now","another rror"},entity.getAllValidationErrors());
    }

    @Test
    public void getAllValidationErrorsWorksCorrectly(){
        entity.addField(new EntityField("someField",FieldType.DATE,Object.class));
        entity.addField(new EntityField("name",FieldType.TEXT,Object.class));

        entity.addFieldValidationError("error3535","someField");
        assertArrayEquals(new String[]{"error3535"},entity.getAllValidationErrors());

        entity.addGlobalValidationError("ok");
        assertArrayEquals(new String[]{"error3535","ok"},entity.getAllValidationErrors());
        entity.addFieldValidationError("error again","name");
        entity.addFieldValidationError("none","someField");
        entity.addGlobalValidationError("an error has occurred!");
        String[] allErrs = entity.getAllValidationErrors();
        Arrays.sort(allErrs);
        assertArrayEquals(new String[]{"an error has occurred!","error again","error3535","none","ok"}, allErrs);

        entity.addGlobalValidationError(null);
        entity.addFieldValidationError(null,null);
        entity.addFieldValidationError(null,"name");
        assertArrayEquals(new String[]{"an error has occurred!","error again","error3535","none","ok"}, allErrs);
    }
}
