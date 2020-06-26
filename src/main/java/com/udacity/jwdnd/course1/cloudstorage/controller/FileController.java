package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController extends BaseUserRelatedController<File> {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        super(fileService);
        this.fileService = fileService;
    }

    @Override
    @ModelAttribute("files")
    public List<File> fetchAll() {
        return super.fetchAll();
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            fileService.uploadFile(file);
            redirectAttributes
                    .addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());
        } catch (IOException ex) {
            redirectAttributes
                    .addFlashAttribute("message", "File upload failed, an error occurred: " + ex.getMessage());
        }

        return "redirect:/result?file";
    }
}