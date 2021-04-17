package com.example.demo.repositories;

import com.example.demo.models.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    // Check if a supplier with the provided name already exists in the table
    Supplier findByName(String name);

    //Query_3 – Local Suppliers
    // Get all suppliers that do not import parts from abroad. Get their id, name and
    // the number of parts they can offer to supply.
    List<Supplier> findAllByImporterIsFalse();
}
