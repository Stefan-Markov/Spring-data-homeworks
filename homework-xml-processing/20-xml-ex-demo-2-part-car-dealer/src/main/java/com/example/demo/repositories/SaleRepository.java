package com.example.demo.repositories;

import com.example.demo.models.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    //Query_6
    List<Sale> findAll();
}
