package com.eafit.backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BackendApplication.class, args);

		String pathToServiceAccountJson = BackendApplication.class.getClassLoader().getResource("static/key.json").getPath();

        // Carga las credenciales desde el archivo JSON
        FileInputStream serviceAccount = new FileInputStream(pathToServiceAccountJson);

        // Configura Firebase Admin SDK
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
	}

}
