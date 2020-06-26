package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.BaseUserRelatedEntity;
import com.udacity.jwdnd.course1.cloudstorage.service.BaseUserRelatedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

public abstract class BaseUserRelatedController<E extends BaseUserRelatedEntity> extends BaseController<E>{

    private BaseUserRelatedService baseUserRelatedService;

    public BaseUserRelatedController(BaseUserRelatedService baseUserRelatedService) {
        super(baseUserRelatedService);
        this.baseUserRelatedService = baseUserRelatedService;
    }

    @GetMapping("/audit")
    public List<E> fetchByUserId(@RequestParam("userId") Long userId){
        return baseUserRelatedService.fetchByUserId(userId);
    }
}
