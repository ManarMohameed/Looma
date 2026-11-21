package com.looma.looma.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.looma.looma.entity.WardrobeItem;
import com.looma.looma.service.WardrobeBrowseService;

@RestController
@RequestMapping("/api/items")
public class WardrobeBrowseController {


	    private final WardrobeBrowseService browseService;

	    public WardrobeBrowseController(WardrobeBrowseService browseService) {
	        this.browseService = browseService;
	    }

	    @GetMapping
	    public List<WardrobeItem> getItems(
	            @RequestParam("userId") Integer userId,
	            @RequestParam(name = "category", required = false) String category) {
	        return browseService.getItems(userId, category);
	    }
	}

