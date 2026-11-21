package com.looma.looma.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ImageStorageService {

	    public static final Path UPLOAD_DIR = Paths.get("uploads").toAbsolutePath().normalize();

	    private static final Map<String, String> ALLOWED_TYPES = Map.of(
	            "image/jpeg", ".jpg",
	            "image/png", ".png",
	            "image/webp", ".webp",
	            "image/gif", ".gif");

	    public String save(MultipartFile file) {
	        if (file == null || file.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
	        }

	        String extension = ALLOWED_TYPES.get(file.getContentType());
	        if (extension == null) {
	            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
	                    "Only JPG, PNG, WEBP or GIF images are allowed");
	        }

	        String fileName = UUID.randomUUID() + extension;

	        try {
	            Files.createDirectories(UPLOAD_DIR);
	            try (InputStream in = file.getInputStream()) {
	                Files.copy(in, UPLOAD_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
	            }
	        } catch (IOException e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not save image");
	        }

	        return "/uploads/" + fileName;
	    }
	}

