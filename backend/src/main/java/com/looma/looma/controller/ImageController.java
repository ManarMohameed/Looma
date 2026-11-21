package com.looma.looma.controller;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.looma.looma.service.ImageStorageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	    private final ImageStorageService imageStorageService;

	    public ImageController(ImageStorageService imageStorageService) {
	        this.imageStorageService = imageStorageService;
	    }

	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
	        String imageUrl = imageStorageService.save(file);
	        return Map.of("imageUrl", imageUrl);
	    }
	}

