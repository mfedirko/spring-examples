package com.example.web.controller;

import com.example.core.context.UserContextWrapper;
import com.example.core.domain.entity.Entity;
import com.example.core.domain.entity.EntityField;
import com.example.core.domain.user.User;
import com.example.web.form.DynamicEntityForm;
import com.example.web.service.DynamicFormService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {
    private static final String DYNAMIC_FORM_VIEW = "dynamicform";
    @Autowired
    private DynamicFormService dynamicFormService;

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody UserContextWrapper userDetails(){
        return UserContextWrapper.getCurrentUserContext();
    }

    @RequestMapping(path = "/form/user", method = RequestMethod.GET)
    public String showForm(ModelMap modelMap) throws Exception {
        DynamicEntityForm form = dynamicFormService.getFormForSource(new User());
        modelMap.addAttribute("entity",form.getEntity());
        modelMap.addAttribute("entityName",form.getEntity().getName());
        System.out.println(modelMap);
        return DYNAMIC_FORM_VIEW;
    }

    @RequestMapping(path = "/form/user", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("entity") Entity entity, BindingResult bindingResult, ModelMap modelMap) throws Exception {
        System.out.println(entity);
        return DYNAMIC_FORM_VIEW;
    }
}
