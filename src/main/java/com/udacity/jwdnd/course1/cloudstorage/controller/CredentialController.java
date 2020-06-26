package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController extends BaseUserRelatedController<Credential> {

    private CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        super(credentialService);
        this.credentialService = credentialService;
    }

    @Override
    @ModelAttribute("credentials")
    public List<Credential> fetchAll() {
        return super.fetchAll();
    }
}
