package pl.zajavka.infrastructure.database.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.model.EmployeeEntity;

@Repository
public interface EmployeeDataJpaRepository extends JpaRepository<EmployeeEntity, Integer> {
}
