package com.example.core.util.masking;

import com.example.core.annotation.Masked;
import com.example.core.context.RequestContextWrapper;
import com.example.core.domain.entity.EntityField;
import com.example.core.domain.entity.FieldType;
import java.lang.annotation.Annotation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class MaskingUtilIntegrationTest {
    private static Masked defaultMaskingWithRoleException;
    private static Masked defaultUnmaskedWithRoleException;
    private static Masked defaultMaskingWithModuleException;
    private static Masked defaultUnmaskedWithModuleException;
    private static Masked defaultMaskingWithNoExceptions;
    private static Masked defaultUnmaskedWithNoExceptions;
    private static Masked defaultMaskingWithRoleModuleExceptions;
    private static Masked defaultUnmaskedWithRoleModuleExceptions;

    private MaskingUtil maskingUtil = new MaskingUtil();


    private static String EXCEPTION_MODULES[] = new String[]{"ACCOUNTS","OTHER"};
    private static String EXCEPTION_ROLES[] = new String[]{"ROLE_OK","ROLE_ABC"};
    private static String[] NO_EXCEPTION = new String[0];
    @BeforeClass
    public static void setup(){
        defaultMaskingWithModuleException = new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.MASKED;
            }

            @Override
            public String[] exceptRoles() {
                return NO_EXCEPTION;
            }

            @Override
            public String[] exceptModules() {
                return EXCEPTION_MODULES;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };

        defaultMaskingWithNoExceptions = new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.MASKED;
            }

            @Override
            public String[] exceptRoles() {
                return NO_EXCEPTION;
            }

            @Override
            public String[] exceptModules() {
                return NO_EXCEPTION;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };
        defaultMaskingWithRoleException = new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.MASKED;
            }

            @Override
            public String[] exceptRoles() {
                return EXCEPTION_ROLES;
            }

            @Override
            public String[] exceptModules() {
                return NO_EXCEPTION;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };

        defaultMaskingWithRoleModuleExceptions= new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.MASKED;
            }

            @Override
            public String[] exceptRoles() {
                return EXCEPTION_ROLES;
            }

            @Override
            public String[] exceptModules() {
                return EXCEPTION_MODULES;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };




        defaultUnmaskedWithModuleException = new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.UNMASKED;
            }

            @Override
            public String[] exceptRoles() {
                return NO_EXCEPTION;
            }

            @Override
            public String[] exceptModules() {
                return EXCEPTION_MODULES;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };

        defaultUnmaskedWithNoExceptions = new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.UNMASKED;
            }

            @Override
            public String[] exceptRoles() {
                return NO_EXCEPTION;
            }

            @Override
            public String[] exceptModules() {
                return NO_EXCEPTION;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };
        defaultUnmaskedWithRoleException = new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.UNMASKED;
            }

            @Override
            public String[] exceptRoles() {
                return EXCEPTION_ROLES;
            }

            @Override
            public String[] exceptModules() {
                return NO_EXCEPTION;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };

        defaultUnmaskedWithRoleModuleExceptions= new Masked(){
            @Override
            public MaskingStrategy strategy() {
                return MaskingStrategy.UNMASKED;
            }

            @Override
            public String[] exceptRoles() {
                return EXCEPTION_ROLES;
            }

            @Override
            public String[] exceptModules() {
                return EXCEPTION_MODULES;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Masked.class;
            }
        };

    }
    EntityField field;

    @Before
    public void setupTest(){
         field = new EntityField("pwd", FieldType.TEXT,Object.class);
        field.setValue("a test value");
        field.setMasked(false);
        RequestContextWrapper.getCurrentContext().setCurrentModule("UNMATCHED");

    }

    // User context integration tests

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ADMIN","OK"})
    public void maskingWithRoleExceptionAndMatchingRole(){
        maskingUtil.processMasking(field,defaultMaskingWithRoleException);
        assertEquals(field.getValue(),"a test value");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultMaskingWithRoleModuleExceptions);
        assertEquals(field.getValue(),"a test value");


    }

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ADMIN","OTHERADMIN"})
    public void maskingWithRoleExceptionAndNoMatchingRole(){
        maskingUtil.processMasking(field,defaultMaskingWithRoleException);
        assertEquals(field.getValue(),"************");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultMaskingWithRoleModuleExceptions);
        assertEquals(field.getValue(),"************");
    }

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ADMIN","OK"})
    public void notmaskingWithRoleExceptionAndMatchingRole(){
        maskingUtil.processMasking(field,defaultUnmaskedWithRoleException);
        assertEquals(field.getValue(),"************");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultUnmaskedWithRoleModuleExceptions);
        assertEquals(field.getValue(),"************");

    }

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ADMIN","OTHERADMIN"})
    public void notmaskingWithRoleExceptionAndNoMatchingRole(){

        maskingUtil.processMasking(field,defaultUnmaskedWithRoleException);
        assertEquals(field.getValue(),"a test value");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultUnmaskedWithRoleModuleExceptions);
        assertEquals(field.getValue(),"a test value");

    }






    // RequestContext integration tests



    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ANY","NOTMATCHWD"})
    public void maskingWithModuleExceptionAndMatchingModule(){
        RequestContextWrapper.getCurrentContext().setCurrentModule("ACCOUNTS");
        maskingUtil.processMasking(field,defaultMaskingWithModuleException);
        assertEquals(field.getValue(),"a test value");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultMaskingWithRoleModuleExceptions);
        assertEquals(field.getValue(),"a test value");


    }

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ANY","NOTMATCHWD"})
    public void maskingWithModuleExceptionAndNoMatchingModule(){
        maskingUtil.processMasking(field,defaultMaskingWithModuleException);
        assertEquals(field.getValue(),"************");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultMaskingWithRoleModuleExceptions);
        assertEquals(field.getValue(),"************");
    }

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ANY","NOTMATCHWD"})
    public void notmaskingWithModuleExceptionAndMatchingModule(){
        RequestContextWrapper.getCurrentContext().setCurrentModule("OTHER");
        maskingUtil.processMasking(field,defaultUnmaskedWithModuleException);
        assertEquals(field.getValue(),"************");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultUnmaskedWithRoleModuleExceptions);
        assertEquals(field.getValue(),"************");

    }

    @Test
    @WithMockUser(username = "user5", password = "pwd2", roles = {"ANY","NOTMATCHWD"})
    public void notmaskingWithModuleExceptionAndNoMatchingModule(){

        maskingUtil.processMasking(field,defaultUnmaskedWithModuleException);
        assertEquals(field.getValue(),"a test value");

        field.setMasked(false);
        maskingUtil.processMasking(field,defaultUnmaskedWithRoleModuleExceptions);
        assertEquals(field.getValue(),"a test value");

    }

    @Test
    public void noExceptionsMasking(){
        maskingUtil.processMasking(field,defaultMaskingWithNoExceptions);
        assertEquals(field.getValue(),"************");
    }


    @Test
    public void noExceptionsUnmasked(){
        maskingUtil.processMasking(field,defaultUnmaskedWithNoExceptions);
        assertEquals(field.getValue(),"a test value");
    }
}