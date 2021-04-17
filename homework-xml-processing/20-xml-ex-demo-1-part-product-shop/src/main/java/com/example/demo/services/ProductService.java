package com.example.demo.services;

import com.example.demo.models.dtos.ProductSeedDto;
import com.example.demo.models.entities.Product;

import java.util.List;

public interface ProductService {
    void seedProducts(List<ProductSeedDto> productSeedDtos);
}
