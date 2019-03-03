package com.example.core.domain.user;

import com.example.core.annotation.IEntityField;
import com.example.core.annotation.Masked;
import com.example.core.domain.IEntity;
import com.example.core.domain.entity.FieldType;
import com.example.core.domain.role.Role;
import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "APP_USR")
public class User implements IEntity {

    @IEntityField(label = "Username", fieldType = FieldType.TEXT)
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @IEntityField(label = "Password",fieldType = FieldType.TEXT)
    @Masked
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE")
    private boolean active;

    @IEntityField(label = "Email",fieldType = FieldType.TEXT)
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @IEntityField(label = "Joining Date",fieldType = FieldType.DATE)
    @Column(name = "CRE_TS")
    @Temporal( TemporalType.DATE)
    private Date joinedDate;

    @IEntityField(fieldType = FieldType.INTEGRAL_NUMBER,label = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToMany( fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String entityName() {
        return "User";
    }
//    private String

    @Transient
    @IEntityField(label = "Day Of Week",fieldType = FieldType.ENUMERATED)
    private FieldType dayOfWeek;


    public FieldType getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(FieldType dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
