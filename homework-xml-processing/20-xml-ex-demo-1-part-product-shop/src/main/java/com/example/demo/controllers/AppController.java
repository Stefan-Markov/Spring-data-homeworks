package com.example.demo.controllers;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.models.dtos.CategorySeedRootDto;
import com.example.demo.models.dtos.ProductSeedRootDto;
import com.example.demo.models.dtos.UserSeedRootDto;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import com.example.demo.utils.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

import static com.example.demo.constants.GlobalConstants.CATEGORIES_FILE_PATH;
import static com.example.demo.constants.GlobalConstants.PRODUCTS_FILE_PATH;

@Component
public class AppController implements CommandLineRunner {
    private final XmlParser xmlParser;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public AppController(XmlParser xmlParser, UserService userService, ProductService productService, CategoryService categoryService) {
        this.xmlParser = xmlParser;
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedCategories();
        this.seedUsers();
        this.seedProducts();
    }

    private void seedCategories() throws FileNotFoundException, JAXBException {
        CategorySeedRootDto categorySeedRootDto = this.xmlParser
                .unmarshalFromFile(CATEGORIES_FILE_PATH, CategorySeedRootDto.class);
        this.categoryService.seedCategories(categorySeedRootDto.getCategories());
    }

    private void seedProducts() throws FileNotFoundException, JAXBException {
       ProductSeedRootDto productSeedRootDto = this.xmlParser
                .unmarshalFromFile(PRODUCTS_FILE_PATH, ProductSeedRootDto.class);
       this.productService.seedProducts(productSeedRootDto.getProducts());
    }

    private void seedUsers() throws FileNotFoundException, JAXBException {
        UserSeedRootDto userSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.USERS_FILE_PATH, UserSeedRootDto.class);
        System.out.println();
        this.userService.seedUsers(userSeedRootDto.getUsers());
    }
}
