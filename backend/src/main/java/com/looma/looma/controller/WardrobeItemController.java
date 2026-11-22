package com.looma.looma.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.looma.looma.entity.WardrobeItem;
import com.looma.looma.service.WardrobeItemService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/items")
public class WardrobeItemController {

    private final WardrobeItemService itemService;

    public WardrobeItemController(WardrobeItemService itemService) {
        this.itemService = itemService;
    }

    public record ItemRequest(
            @NotNull Integer userId,
            @NotBlank @Size(max = 100) String itemName,
            @NotBlank String category,
            @Size(max = 50) String brand,
            @Size(max = 50) String productNumber,
            @Size(max = 255) String imageUrl) {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WardrobeItem create(@Valid @RequestBody ItemRequest request) {
        return itemService.create(request.userId(), request.itemName(), request.category(),
                request.brand(), request.productNumber(), request.imageUrl());
    }

    @PutMapping("/{itemId}")
    public WardrobeItem update(@PathVariable("itemId") Integer itemId,
                               @Valid @RequestBody ItemRequest request) {
        return itemService.update(itemId, request.itemName(), request.category(),
                request.brand(), request.productNumber(), request.imageUrl());
    }

    @GetMapping("/{itemId}")
    public WardrobeItem getById(@PathVariable("itemId") Integer itemId) {
        return itemService.getById(itemId);
    }
}