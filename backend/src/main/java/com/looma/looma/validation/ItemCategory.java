package com.looma.looma.validation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemCategory {


	    public static final List<String> ALLOWED =
	            List.of("Tops", "Bottoms", "Dresses", "Shoes", "Accessories");

	    private ItemCategory() {
	    }

	    public static String normalize(String category) {
	        if (category != null) {
	            String trimmed = category.trim();
	            for (String allowed : ALLOWED) {
	                if (allowed.equalsIgnoreCase(trimmed)) {
	                    return allowed;
	                }
	            }
	        }
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
	                "Invalid category. Allowed: " + String.join(", ", ALLOWED));
	    
	}
}
