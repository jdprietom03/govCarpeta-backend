package com.eafit.backend.shared.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eafit.backend.shared.client.firebase.FireStorageClient;
import com.eafit.backend.shared.dtos.UploadDocumentDto;
import com.eafit.backend.shared.entity.Documento;
import com.eafit.backend.shared.entity.Usuario;
import com.eafit.backend.shared.repository.DocumentoRepository;

@Service
public class FileService {
    @Autowired
    private FireStorageClient fireStorageClient;

    @Autowired
    private DocumentoRepository documentoRepository;

    private final String PATH = "govcarpeta-6c1cc.appspot.com"; 

    public void upload(UploadDocumentDto documentDto) throws IOException {
        String url = fireStorageClient.uploadFile(documentDto.file, PATH);

        Usuario usuario = new Usuario();
        usuario.setId(documentDto.user_id);

        Documento document = new Documento();
        document.setUrl(url);
        document.setUsuario(usuario);
        document.setNombre(documentDto.filename);

        documentoRepository.save(document);
    }
}
