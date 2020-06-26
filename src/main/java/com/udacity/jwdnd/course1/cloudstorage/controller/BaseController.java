package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.BaseEntity;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.BaseService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

public abstract class BaseController<E extends BaseEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);


    private BaseService<E> baseService;
    protected static final String HOME_REDIRECT = "redirect:/home";
    protected static final String RESULT_REDIRECT_SUCCESS_NOTE = "redirect:/result?note";
    protected static final String RESULT_REDIRECT_SUCCESS_CREDENTIAL = "redirect:/result?credential";
    protected static final String RESULT_REDIRECT_UPDATE_NOTE = "redirect:/result?noteupdate";
    protected static final String RESULT_REDIRECT_UPDATE_CREDENTIAL = "redirect:/result?credentialupdate";
    protected static final String RESULT_REDIRECT_DELETE = "redirect:/result?delete";

    public BaseController(BaseService<E> baseService) {
        this.baseService = baseService;
    }

    public List<E> fetchAll() { return baseService.fetchAll(); }

    @GetMapping("/{id}")
    public E fetchById(@PathVariable Long id) throws NotFoundException { return baseService.fetchById(id); }

    @PostMapping
    public String save(@ModelAttribute @Valid E entity) {

        baseService.save(entity);
        return correctURL(entity,"save");
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute @Valid E entity) throws NotFoundException {
        baseService.update(entity, entity.getId());
        return correctURL(entity,"update");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws NotFoundException {
        baseService.delete(id);
        return RESULT_REDIRECT_DELETE;
    }

    private String correctURL(E entity , String action){

        if(action.equals("save")){
            if(entity instanceof  Note){
                return RESULT_REDIRECT_SUCCESS_NOTE;
            }else if(entity instanceof Credential){
                return RESULT_REDIRECT_SUCCESS_CREDENTIAL;
            } else{
                return HOME_REDIRECT;
            }
        } else if(action.equals("update")){
            if(entity instanceof  Note){
                return RESULT_REDIRECT_UPDATE_NOTE;
            }else if(entity instanceof Credential){
                return RESULT_REDIRECT_UPDATE_CREDENTIAL;
            } else{
                return HOME_REDIRECT;
            }
        } else{
            return HOME_REDIRECT;
        }
    }

}
