package com.example.demo.services.impl;

import com.example.demo.models.dtos.ProductSeedDto;
import com.example.demo.models.entities.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProducts(List<ProductSeedDto> productSeedDtos) {
        if(this.productRepository.count() != 0){
            return;
        }
        productSeedDtos
                .forEach(productSeedDto -> {
                    if(this.validationUtil.isValid(productSeedDto)){
                        Product product = this.modelMapper
                                .map(productSeedDto,Product.class);
                        //There is always a seller
                        product.setSeller(this.userService.getRandomUser());
                        Random random = new Random();
                        //Returns o or 1
                        int randomNum = random.nextInt(2);
                        if(randomNum == 1){
                            // Some of the products will have a Buyer
                            product.setBuyer(this.userService.getRandomUser());
                        }
                        product.setCategories(new HashSet<>(this.categoryService.getRandomCategories()));
                        System.out.println();
                        this.productRepository.saveAndFlush(product);
                    }else {
                        this.validationUtil.getViolations(productSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }


}
