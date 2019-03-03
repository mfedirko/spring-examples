package com.example.core.domain.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum FieldType {
    TEXT{
        @Override
        public String toString(){
            return "Text";
        }
    },
    INTEGRAL_NUMBER{
        @Override
        public String toString(){
            return "Integer";
        }
    },
    DECIMAL_NUMBER{
        @Override
        public String toString(){
            return "Decimal";
        }
    },
    BOOLEAN{
        @Override
        public String toString(){
            return "True/False";
        }
    },
    ENUMERATED{
        @Override
        public String toString(){
            return "Enumerated";
        }
    },
    DATE{
        @Override
        public String toString(){
            return "Date";
        }
    };

    private static Map<FieldType,Class[]> baseAllowedTypes = new HashMap<>();
    static {
        baseAllowedTypes.put(DATE,new Class[]{Temporal.class,Date.class,java.sql.Date.class});
        baseAllowedTypes.put(TEXT,new Class[]{String.class});
        baseAllowedTypes.put(INTEGRAL_NUMBER,new Class[]{Integer.class, BigInteger.class,Long.class,Short.class,Byte.class,
                                                            int.class,short.class,byte.class,long.class});
        baseAllowedTypes.put(BOOLEAN,new Class[]{Boolean.class,boolean.class});
        baseAllowedTypes.put(ENUMERATED,new Class[]{Enum.class});
        baseAllowedTypes.put(DECIMAL_NUMBER,new Class[]{Float.class,BigDecimal.class,Double.class,double.class, float.class});


    }



    protected static Set<FieldType> getAllAssignableToClass(Class clazz){
        if (clazz == null) return Collections.emptySet();
        Set<FieldType> allMappable = Collections.emptySet();
        for (Map.Entry<FieldType, Class[]> entry : baseAllowedTypes.entrySet()) {
            for (Class allowed : entry.getValue()) {
                if (allowed.isAssignableFrom(clazz)) {
                    allMappable.add(entry.getKey());
                    break;
                }
            }
        }
        return allMappable;
    }

    public boolean isAssignable(Class clazz){
        if (clazz == null) return false;
        return Arrays.asList(baseAllowedTypes.get(this))
                .stream()
                .anyMatch(allowed -> allowed.isAssignableFrom(clazz));
    }
}
