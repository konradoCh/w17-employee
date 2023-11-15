package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.infrastructure.database.jparepositories.EmployeeDataJpaRepository;
import pl.zajavka.infrastructure.database.model.EmployeeEntity;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeDataJpaRepository employeeDataJpaRepository;

    @Transactional
    public EmployeeEntity create(EmployeeEntity employee) {
        return employeeDataJpaRepository.save(employee);
    }

    public List<EmployeeEntity> findAll() {
        return employeeDataJpaRepository.findAll();
    }
    @Transactional
    public void runSuccessful() {
        employeeDataJpaRepository.deleteAll();


        EmployeeEntity employee1 = employeeDataJpaRepository.save(EmployeeData.someEmployee1());
        EmployeeEntity employee2 = employeeDataJpaRepository.save(EmployeeData.someEmployee2());
        EmployeeEntity employee3 = employeeDataJpaRepository.save(EmployeeData.someEmployee3());

        System.out.println("###Employee 1: " + employeeDataJpaRepository.findById(employee1.getEmployeeId()));
        System.out.println("###Employee 2: " + employeeDataJpaRepository.findById(employee2.getEmployeeId()));

        EmployeeEntity employeeBeforeUpdate = employeeDataJpaRepository.findById(employee3.getEmployeeId()).orElseThrow();
        employeeBeforeUpdate.setSalary(new BigDecimal("10348.91"));
        employeeDataJpaRepository.saveAndFlush(employeeBeforeUpdate);

        System.out.println("###Employee updated: "
                + employeeDataJpaRepository.findById(employee3.getEmployeeId()));

        employeeDataJpaRepository.findAll()
                .forEach(employee -> System.out.println("###Employee: " + employee));

        employeeDataJpaRepository.deleteById(employee2.getEmployeeId());

        employeeDataJpaRepository.findAll()
                .forEach(employee -> System.out.println("###Employee: " + employee));
    }

    @Transactional
    public void testTransactional() {
        employeeDataJpaRepository.deleteAll();
        employeeDataJpaRepository.flush();

        employeeDataJpaRepository.saveAll(
                List.of(
                        EmployeeData.someEmployee1(),
                        EmployeeData.someEmployee2(),
                        EmployeeData.someEmployee3(),
                        EmployeeData.someEmployee3()
                )
        );

    }
}
