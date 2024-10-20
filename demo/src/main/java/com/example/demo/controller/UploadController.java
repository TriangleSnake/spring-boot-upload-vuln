package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.springframework.util.StringUtils;


@Controller
public class UploadController {

    @GetMapping("/upload")
    public String uploadPage() {
        return "uploadFile";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, 
                                   @RequestParam("path") String path, 
                                   Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            return "uploadFile";
        }

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(path);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (java.io.InputStream inputStream = file.getInputStream()) {
                Files.copy(
                    inputStream,
                    uploadPath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException ioe) {
                throw new IOException("Could not save file: " + fileName, ioe);
            }
        }

        return "uploadFile";
    }
}
