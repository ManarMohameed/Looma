package com.looma.looma.service;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.looma.looma.entity.WardrobeItem;
import com.looma.looma.repository.WardrobeItemRepository;

@Service
public class WardrobeBrowseService {

	    private final WardrobeItemRepository itemRepository;

	    public WardrobeBrowseService(WardrobeItemRepository itemRepository) {
	        this.itemRepository = itemRepository;
	    }

	    public List<WardrobeItem> getItems(Integer userId, String category) {
	        if (category == null || category.isBlank()) {
	            return itemRepository.findByUserId(userId);
	        }
	        return itemRepository.findByUserIdAndCategoryIgnoreCase(userId, category.trim());
	    }

	    public void deleteItem(Integer itemId) {
	        if (!itemRepository.existsById(itemId)) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
	        }
	        itemRepository.deleteById(itemId);
	    }
	}
	

