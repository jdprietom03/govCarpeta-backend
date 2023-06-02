package com.eafit.backend.shared.client.firebase;

import com.google.firebase.cloud.StorageClient;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class FireStorageClient {
    
    public String uploadFile(MultipartFile file, String storagePath) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                getClass().getResourceAsStream("/static/key.json")
        );

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        String fileName = file.getOriginalFilename();
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

        BlobId blobId = BlobId.of(storagePath, uniqueFileName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        Blob blob = storage.create(blobInfo, file.getBytes());
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // String downloadUrl = blob.getMediaLink();

        String previewUrl = blob.signUrl(3600, TimeUnit.SECONDS)
        .toString();

        return previewUrl;

    }
    
}
