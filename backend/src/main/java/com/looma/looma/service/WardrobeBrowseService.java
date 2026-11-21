package com.looma.looma.service;
import java.util.List;

import org.springframework.stereotype.Service;

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
	}

