package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseUserRelatedEntity;
import com.udacity.jwdnd.course1.cloudstorage.mapper.BaseUserRelatedMapper;
import com.udacity.jwdnd.course1.cloudstorage.security.entity.PrincipleUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

public class BaseUserRelatedServiceImpl<E extends BaseUserRelatedEntity> extends BaseServiceImpl<E> implements BaseUserRelatedService<E> {

    private BaseUserRelatedMapper baseUserRelatedMapper;

    public BaseUserRelatedServiceImpl(BaseUserRelatedMapper baseUserRelatedMapper) {
        super(baseUserRelatedMapper);
        this.baseUserRelatedMapper = baseUserRelatedMapper;
    }

    @Override
    public List<E> fetchByUserId(Long userId) {
        return baseUserRelatedMapper.findAllByUserId(userId);
    }

    @Override
    public List<E> fetchAll() {
        return fetchByLoggedInUser();
    }

    @Override
    public E save(E entity) {
        entity.setUserId(getPrincipleUser().getId());
        return super.save(entity);
    }

    protected List<E> fetchByLoggedInUser() {
        return fetchByUserId(getPrincipleUser().getId());
    }

    protected PrincipleUser getPrincipleUser() throws AccessDeniedException {
        if(isAuthenticated()) {
            return (PrincipleUser) authentication().getPrincipal();
        } else throw new AccessDeniedException("Unauthorized operation. Authentication required");
    }

    private Boolean isAuthenticated() { return authentication().isAuthenticated(); }

    private Authentication authentication() { return SecurityContextHolder.getContext().getAuthentication(); }
}
