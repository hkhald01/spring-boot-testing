package net.javaguides.repository;

import net.javaguides.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {
  @Autowired private EmployeeRepository repository;
  Employee employee;

  @BeforeEach
  void setup() {
    employee =
        Employee.builder()
            .firstName("Heikel")
            .lastName("Khaldi")
            .email("heikel.khaldi1@gmail.com")
            .build();
  }

  @Test
  // Junit test for save employee operation
  // @DisplayName("JUnit test for save employee operation")
  void givenEmployeeObject_whenSave_thenReturnSavedObject() {
    // given - precondition or setup

    // when - action or the behavior that we are going to test
    Employee savedEmployee = repository.save(employee);

    // then - verify the output
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isPositive();

    assertThat(savedEmployee.getFirstName()).isEqualTo("Heikel");
  }
  // JUnit test for get all employees
  @Test
  void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
    // given - precondition or setup
    Employee employee2 =
        Employee.builder()
            .firstName("HeFirasikel")
            .lastName("Khaldi")
            .email("firas.khaldi1@gmail.com")
            .build();

    repository.save(employee);
    repository.save(employee2);

    // when - action or the behavior that we are going to test
    List<Employee> employeeList = repository.findAll();

    // then - verify the output
    assertThat(employeeList).hasSize(2);
  }

  // JUnit test for get employee by id
  @Test
  void givenEmployee_whenFindById_thenReturnEmployee() {
    // given - precondition or setup
    repository.save(employee);

    // when - action or the behavior that we are going to test
    Employee employeeFromDB = null;
    Optional<Employee> byId = repository.findById(employee.getId());
    if (byId.isPresent()) {
      employeeFromDB = byId.get();
    }
    // then - verify the output
    assertThat(employeeFromDB).isNotNull();
  }

  // JUnit test for get Employee by email
  @Test
  void givenEmployee_whenFindByEmail_thenReturnEmployee() {
    // given - precondition or setup

    repository.save(employee);
    // when - action or the behavior that we are going to test
    Employee employeeFromDB = repository.findByEmail(employee.getEmail()).get();
    // then - verify the output

    assertThat(employeeFromDB).isNotNull();
  }

  // JUnit test for update employee
  @Test
  void givenEmployee_whenUpdate_thenReturnUpdatedEmployee() {
    // given - precondition or setup
    repository.save(employee);
    // when - action or the behavior that we are going to test
    Employee employeeFromDB = repository.findByEmail(employee.getEmail()).get();
    employeeFromDB.setFirstName("Haykel");

    // then - verify the output
    Employee employeeFromDBAfterUpdate = repository.save(employeeFromDB);

    assertThat(employeeFromDBAfterUpdate.getFirstName()).isEqualTo("Haykel");
  }

  // JUnit test for
  @Test
  void givenEmployee_whenDeleteEmployee_thenRemoveTheEmployee() {
    // given - precondition or setup
    repository.save(employee);
    // when - action or the behavior that we are going to test
    repository.delete(employee);
    // then - verify the output
    Optional<Employee> optionalEmployee = repository.findById(employee.getId());

    assertThat(optionalEmployee).isEmpty();
  }

  // JUnit test for
  @Test
  void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
    // given - precondition or setup
    repository.save(employee);
    // when - action or the behavior that we are going to test
    String firstName = "Heikel";
    String lastName = "Khaldi";
    Employee employeeByJPQL = repository.findByJPQL(firstName, lastName);

    // then - verify the output

    assertThat(employeeByJPQL.getFirstName()).isEqualTo("Heikel");
    assertThat(employeeByJPQL.getLastName()).isEqualTo("Khaldi");
  }

  // JUnit test for named jpql with params
  @Test
  void givenFirstNameAndLastName_whenFindByJPQLParams_thenReturnEmployee() {
    // given - precondition or setup
    repository.save(employee);
    // when - action or the behavior that we are going to test
    String firstName = "Heikel";
    String lastName = "Khaldi";

    Employee employeeFromDB = repository.findByJPQLNamedParams(firstName, lastName);
    // then - verify the output

    assertThat(employeeFromDB).isNotNull();
  }

  // JUnit test for
  @Test
  void givenFirstNameAndLastName_whenFindByNativeQuery_thenReturnEmployee() {
    // given - precondition or setup
    repository.save(employee);
    // when - action or the behavior that we are going to test
    String firstName = employee.getFirstName();
    String lastName = employee.getLastName();
    Employee employeefromDB = repository.findByNativeSQL(firstName, lastName);
    // then - verify the output

    assertThat(employeefromDB).isNotNull();
  }

  @Test
  void givenFirstNameAndLastName_whenFindByNativeQueryNamedParams_thenReturnEmployee() {
    // given - precondition or setup
    repository.save(employee);
    // when - action or the behavior that we are going to test
    String firstName = employee.getFirstName();
    String lastName = employee.getLastName();
    Employee employeefromDB = repository.findByNativeSQLNamedParams(firstName, lastName);
    // then - verify the output

    assertThat(employeefromDB).isNotNull();
  }
}
