package com.example.core.domain.mapper;

import com.example.core.domain.entity.FieldType;
import com.example.core.domain.user.User;
import com.example.core.domain.entity.Entity;
import com.example.core.service.SingleSourceEntityMapper;
import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EntityFieldMapperTest {
    private SingleSourceEntityMapper mapper = new SingleSourceEntityMapper();
    private Entity entity;

    @Before
    public void setup(){
        entity = new Entity();
    }


    @Test
    public void mapNullToEntityReturnsNull() throws Exception {
        Entity mapped = mapper.mapToEntity(null);
        assertNull(mapped);
    }

    @Test
    public void mapToEntity_User() throws Exception {
        User u = new User();
        u.setEmail("abc@ex.com");
        u.setUsername("admin1");
        u.setPassword("potato123");
        Date fiveMinsAgo = new Date(new Date().getTime() - 5*60*1000 );
        u.setJoinedDate(fiveMinsAgo);
        u.setId(24241);

        entity = mapper.mapToEntity(u);
        System.out.println(entity);
        assertEquals(entity.getName(),"User");
        assertEquals(entity.getFields().size(),5);
        assertEquals(entity.getField("joinedDate").getValue(),fiveMinsAgo);
        assertEquals(entity.getField("id").getValue(),24241);
        assertEquals(entity.getField("username").getValue(),"admin1");
        assertEquals(entity.getField("email").getValue(),"abc@ex.com");
        assertEquals(entity.getField("password").getValue(),"potato123");

    }

    @Test
    public void mapToSource() throws Exception {
        mapToEntity_User();
        User u = (User)mapper.mapToSource(entity);
        assertEquals(u.getPassword(),"potato123");
        assertEquals(u.getUsername(),"admin1");
        assertEquals(u.getEmail(),"abc@ex.com");
        assertEquals(u.getId(),24241);
        assertNotNull(u.getJoinedDate());
        System.out.println(u.getJoinedDate());
    }

    @Test
    public void enumeratedValueMapped() throws Exception {
        TestEntity testEntity = new TestEntity();
        testEntity.setFieldType(FieldType.DATE);
        Entity entity = mapper.mapToEntity(testEntity);
        assertArrayEquals(entity.getField("fieldType").getValueList(), FieldType.values());
    }




}