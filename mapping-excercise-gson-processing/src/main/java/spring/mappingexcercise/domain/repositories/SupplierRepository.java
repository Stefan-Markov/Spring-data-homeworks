package spring.mappingexcercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.mappingexcercise.domain.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long>{
}
