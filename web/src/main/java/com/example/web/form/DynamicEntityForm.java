package com.example.web.form;

import com.example.core.domain.entity.Entity;
import org.springframework.util.Assert;

public class DynamicEntityForm {
    private boolean readOnly;
    private Entity entity;

    public DynamicEntityForm(Entity entity) {
        Assert.notNull(entity,"Entity cannot be null");
        this.entity = entity;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
