package com.eafit.backend.shared.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UploadDocumentDto {
    public MultipartFile file;
    public String user_id;
    public String filename;
}
    