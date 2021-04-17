package spring.mappingexcercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.mappingexcercise.domain.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
