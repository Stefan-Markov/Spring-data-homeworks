package com.example.demo.services;

import com.example.demo.models.dtos.CategorySeedDto;
import com.example.demo.models.entities.Category;


public interface CategoryService {
    void seedCategories(List<CategorySeedDto> categorySeedDtos);

    List<Category> getRandomCategories();
}
