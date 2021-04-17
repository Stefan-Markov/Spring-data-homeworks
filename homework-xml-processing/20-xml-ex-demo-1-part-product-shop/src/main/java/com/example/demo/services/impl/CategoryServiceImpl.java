package com.example.demo.services.impl;

import com.example.demo.models.dtos.CategorySeedDto;
import com.example.demo.models.entities.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Random random;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper,
                               ValidationUtil validationUtil, Random random) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
    }


    @Override
    public void seedCategories(List<CategorySeedDto> categorySeedDtos) {
        if(this.categoryRepository.count() != 0){
            return;
        }
        categorySeedDtos.stream()
                .forEach(categorySeedDto -> {
                    if(this.validationUtil.isValid(categorySeedDto)){
                        Category category = this.modelMapper.map(categorySeedDto,Category.class);
                        this.categoryRepository.saveAndFlush(category);
                    }else {
                        this.validationUtil.getViolations(categorySeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public List<Category> getRandomCategories() {
        Random random = new Random();
        List<Category> resultList = new ArrayList<>();
        int randomCounter = random.nextInt(3) + 1;

        // iterated from 1 to 3 times
        for (int i = 0; i < randomCounter; i++) {
            // Get a number from 1 to repo.cout()
            long randomId = random
                    .nextInt((int) this.categoryRepository.count()) + 1;
            // On each iteration add a random category to the list
            resultList.add(this.categoryRepository.getOne(randomId));
        }
        // The list contains 1 to 3 random Categories
        return resultList;
    }
}
