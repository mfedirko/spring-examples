package com.example.web.service;

import com.example.core.domain.IEntity;
import com.example.core.domain.entity.Entity;
import com.example.core.service.SingleSourceEntityMapper;
import com.example.core.util.masking.MaskingUtil;
import com.example.web.form.DynamicEntityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class DynamicFormService {

    @Autowired
    private SingleSourceEntityMapper simpleMapper;

    @Autowired
    private Validator validator;

    public DynamicEntityForm getFormForEntity(Entity entity){
        return new DynamicEntityForm(entity);
    }
    public DynamicEntityForm getFormForSource(IEntity source) throws Exception {
        return getFormForEntity(simpleMapper.mapToEntity(source));
    }
    public DynamicEntityForm getReadonlyForm(Entity entity){
        DynamicEntityForm form = new DynamicEntityForm(entity);
        form.setReadOnly(true);
        return form;
    }

    public Errors validateForm(DynamicEntityForm form) throws Exception {
        Object source = simpleMapper.mapToSource(form.getEntity());
        Errors errors = new BeanPropertyBindingResult(source,"source");
        validator.validate(source,errors);
        return errors;
    }
}
