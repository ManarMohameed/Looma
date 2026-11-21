package com.looma.looma.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.looma.looma.service.ImageStorageService;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        String location = ImageStorageService.UPLOAD_DIR.toUri().toString();
	        if (!location.endsWith("/")) {
	            location = location + "/";
	        }
	        registry.addResourceHandler("/uploads/**").addResourceLocations(location);
	    }
	}

