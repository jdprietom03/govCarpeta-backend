package com.eafit.backend.shared.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eafit.backend.shared.dtos.UploadDocumentDto;
import com.eafit.backend.shared.services.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadCertificationRequest(@ModelAttribute UploadDocumentDto documentDto) throws IOException {
        fileService.upload(documentDto);
        return;
    }   
}
