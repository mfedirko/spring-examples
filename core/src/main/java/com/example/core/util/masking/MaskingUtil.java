package com.example.core.util.masking;

import com.example.core.annotation.Masked;
import com.example.core.context.RequestContextWrapper;
import com.example.core.context.UserContextWrapper;
import com.example.core.domain.entity.EntityField;
import java.util.Arrays;

public class MaskingUtil {
    public static void processMasking(EntityField field, Masked maskedAnnotation){
        if (maskedAnnotation == null) return;
        boolean hasRoleException = hasRoleException(maskedAnnotation.exceptRoles());
        boolean hasModuleException = hasModuleException(maskedAnnotation.exceptModules());

        boolean shouldMaskByDefault = false;
        switch (maskedAnnotation.strategy()){
            case MASKED:
                shouldMaskByDefault = true;
                break;
            case UNMASKED:
                shouldMaskByDefault = false;
                break;
        }
        if (hasRoleException || hasModuleException){
            field.setMasked(!shouldMaskByDefault);
        } else {
            field.setMasked(shouldMaskByDefault);
        }

    }
    protected static boolean hasRoleException(String[] roleExceptions){
        boolean hasException = false;
        if (roleExceptions != null){
                String[] currentUserRoles = UserContextWrapper.getCurrentUserContext().getRoles();
                hasException = currentUserRoles != null && Arrays.stream(currentUserRoles)
                        .anyMatch(current -> Arrays.stream(roleExceptions).anyMatch(e -> e.equals(current)));
        }
        return hasException;
    }

    protected static boolean hasModuleException(String[] moduleExceptions){
        boolean hasException = false;
        String module = RequestContextWrapper.getCurrentContext().getCurrentModule();
        if (moduleExceptions != null){
            if (Arrays.asList(moduleExceptions).contains(module)){
               hasException = true;
            }
        }
        return hasException;
    }
}
