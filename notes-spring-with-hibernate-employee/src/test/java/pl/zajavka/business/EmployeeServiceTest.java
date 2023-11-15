package pl.zajavka.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;
import pl.zajavka.infrastructure.database.model.EmployeeEntity;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;


@Testcontainers
@SpringJUnitConfig(classes = {ApplicationConfiguration.class})
class EmployeeServiceTest {

    @Container
    static PostgreSQLContainer<?> postgreSQL = new PostgreSQLContainer<>("postgres:15.0");

    @DynamicPropertySource
    static void postgreSQL(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", postgreSQL::getJdbcUrl);
        registry.add("jdbc.user", postgreSQL::getUsername);
        registry.add("jdbc.pass", postgreSQL::getPassword);
    }

    @Autowired
    private EmployeeService employeeService;

    @Test
    void firstTestForEmployeeFunctionality() {
        // given
        employeeService.create(someEmployee());
        // when
        List<EmployeeEntity> allEmployees = employeeService.findAll();
//         then
        Assertions.assertEquals(1, allEmployees.size());
    }
    private EmployeeEntity someEmployee() {
        return EmployeeEntity.builder()
                .name("Tomek")
                .surname("Romek")
                .salary(BigDecimal.valueOf(9547.11))
                .since(ZonedDateTime.of(2010, 10, 1, 12, 10, 0, 0, ZoneOffset.UTC))
                .build();
    }

}