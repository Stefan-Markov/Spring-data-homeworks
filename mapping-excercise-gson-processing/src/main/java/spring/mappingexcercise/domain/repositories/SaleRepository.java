package spring.mappingexcercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.mappingexcercise.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
