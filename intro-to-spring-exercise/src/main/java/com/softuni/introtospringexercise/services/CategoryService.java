package com.softuni.introtospringexercise.services;

import com.softuni.introtospringexercise.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;

    Category  getCategoryById(Long id);
}
