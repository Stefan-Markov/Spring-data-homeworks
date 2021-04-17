package com.softuni.introtospringexercise.impl;

import com.softuni.introtospringexercise.constants.GlobalConstants;
import com.softuni.introtospringexercise.entities.Category;
import com.softuni.introtospringexercise.repositories.CategoryRepository;
import com.softuni.introtospringexercise.services.CategoryService;
import com.softuni.introtospringexercise.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() != 0) {
            return;
        }

        String[] fileContent =
                this.fileUtil.readFileContent(GlobalConstants.CATEGORIES_FILE_PATH);

        Arrays.stream(fileContent).forEach(r -> {
            Category category = new Category(r);
            this.categoryRepository.saveAndFlush(category);
        });

    }

    @Override
    public Category getCategoryById(Long id) {

        return this.categoryRepository.getOne(id);
    }

}
