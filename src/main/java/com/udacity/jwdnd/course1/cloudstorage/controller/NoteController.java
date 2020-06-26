package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController extends BaseUserRelatedController<Note> {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        super(noteService);
        this.noteService = noteService;
    }

    @Override
    @ModelAttribute("notes")
    public List<Note> fetchAll() {
        return super.fetchAll();
    }
}