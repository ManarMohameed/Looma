package com.looma.looma.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.looma.looma.entity.WardrobeItem;
import com.looma.looma.repository.UserRepository;
import com.looma.looma.repository.WardrobeItemRepository;
import com.looma.looma.validation.ItemCategory;

@Service
public class WardrobeItemService {

    private final WardrobeItemRepository itemRepository;
    private final UserRepository userRepository;

    public WardrobeItemService(WardrobeItemRepository itemRepository,
                               UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public WardrobeItem create(Integer userId, String itemName, String category,
                               String brand, String productNumber, String imageUrl) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        WardrobeItem item = new WardrobeItem();
        item.setUserId(userId);
        item.setItemName(itemName);
        item.setCategory(ItemCategory.normalize(category));
        item.setBrand(brand);
        item.setProductNumber(productNumber);
        item.setImageUrl(imageUrl);
        return itemRepository.save(item);
    }

    public WardrobeItem update(Integer itemId, String itemName, String category,
                               String brand, String productNumber, String imageUrl) {
        WardrobeItem item = getById(itemId);
        item.setItemName(itemName);
        item.setCategory(ItemCategory.normalize(category));
        item.setBrand(brand);
        item.setProductNumber(productNumber);
        item.setImageUrl(imageUrl);
        return itemRepository.save(item);
    }

    public WardrobeItem getById(Integer itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }
}