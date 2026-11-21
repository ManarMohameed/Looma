package com.looma.looma.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.looma.looma.entity.WardrobeItem;

    public interface WardrobeItemRepository extends JpaRepository<WardrobeItem, Integer> {
        
    	List<WardrobeItem> findByUserId(Integer userId);

        List<WardrobeItem> findByUserIdAndCategoryIgnoreCase(Integer userId, String category);
    }