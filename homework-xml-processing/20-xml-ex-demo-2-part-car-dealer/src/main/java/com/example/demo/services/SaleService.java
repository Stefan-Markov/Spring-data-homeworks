package com.example.demo.services;

import com.example.demo.models.dtos.views.query6.SaleViewRootDto;

public interface SaleService {
    void seedSales();
    SaleViewRootDto getAllSales();
}
