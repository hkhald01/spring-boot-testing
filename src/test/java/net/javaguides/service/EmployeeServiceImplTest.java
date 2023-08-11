package net.javaguides.service;

import net.javaguides.exception.ResourceNotFoundException;
import net.javaguides.model.Employee;
import net.javaguides.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

  @InjectMocks private EmployeeServiceImpl employeeService;
  @Mock private EmployeeRepository employeeRepository;
  Employee employee;

  @BeforeEach
  void setup() {
    // employeeRepository = Mockito.mock(EmployeeRepository.class);
    // employeeService = new EmployeeServiceImpl(employeeRepository);
    employee =
        Employee.builder()
            .id(1L)
            .firstName("Heikel")
            .lastName("Khaldi")
            .email("heikel.khaldi1@gmail.com")
            .build();
  }

  // JUnit test for
  @Test
  void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
    // given - precondition or setup
    Employee employee =
        Employee.builder()
            .id(1L)
            .firstName("Heikel")
            .lastName("Khaldi")
            .email("heikel.khaldi1@gmail.com")
            .build();
    // when - action or the behavior that we are going to test
    BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
        .willReturn(Optional.empty());
    BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

    Employee savedEmployee = employeeService.saveEmployee(employee);
    // then - verify the output
    assertThat(savedEmployee).isNotNull();
  }

  @Test
  void givenExistingEmployee_whenSaveEmployee_thenThrowResourceNotFoundException() {
    // given - precondition or setup
    Employee employee =
        Employee.builder()
            .id(1L)
            .firstName("Heikel")
            .lastName("Khaldi")
            .email("heikel.khaldi1@gmail.com")
            .build();
    // when - action or the behavior that we are going to test
    BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
        .willReturn(Optional.of(employee));

    assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
    // then - verify the output
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  // JUnit test for
  @Test
  void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
    Employee employee1 =
        Employee.builder()
            .id(1L)
            .firstName("Heikel")
            .lastName("Khaldi")
            .email("heikel.khaldi1@gmail.com")
            .build();
    Employee employee2 =
        Employee.builder()
            .id(1L)
            .firstName("Firas")
            .lastName("Khaldi")
            .email("firas.khaldi1@gmail.com")
            .build();
    // given - precondition or setup
    BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee1, employee2));
    // when - action or the behavior that we are going to test
    List<Employee> allEmployees = employeeService.getAllEmployees();
    // then - verify the output

    assertThat(allEmployees).hasSize(2);
  }

  // JUnit test for
  @Test
  void givenEmptyEmplyeeList_whenGetAllALlEmployees_thenReturnEmptyEmployeeList() {
    // given - precondition or setup
    BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

    // when - action or the behavior that we are going to test
    List<Employee> allEmployees = employeeService.getAllEmployees();
    // then - verify the output
    assertThat(allEmployees).isNotNull();
    assertThat(allEmployees).isEmpty();
  }

  // JUnit test for
  @Test
  void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
    // given - precondition or setup
    BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
    // when - action or the behavior that we are going to test
    Employee employeeById = employeeService.getEmployeeById(1L).get();
    // then - verify the output
    assertThat(employeeById).isNotNull();
  }

  // JUnit test for
  @Test
  void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    // given - precondition or setup
    BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
    employee.setFirstName("Heikel Updated");
    // when - action or the behavior that we are going to test
    Employee updatedEmployee = employeeService.updateEmployee(employee);
    // then - verify the output
    assertThat(updatedEmployee.getFirstName()).isNotNull().isEqualTo("Heikel Updated");
  }
  // JUnit test for
  @Test
  void givenEmployeeId_whenDeleteEmployee_thenReturnDeletedEmployee() {
    // given - precondition or setup
    long employeeId = 1L;
    BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);
    // when - action or the behavior that we are going to test
    employeeService.deleteEmployee(employeeId);
    // then - verify the output

    verify(employeeRepository, times(1)).deleteById(employeeId);
  }
}
