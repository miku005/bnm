package com.bnm.service;

import com.bnm.entity.Employee;
import com.bnm.exception.ResourceNotFound;
import com.bnm.payload.EmployeeDto;
import com.bnm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
     private EmployeeRepository employeeRepository;
     private ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    public  EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = MapToEntity(dto);
        Employee emp = employeeRepository.save(employee);
        EmployeeDto employeeDto = MapToDto(emp);
        return employeeDto;
    }
     EmployeeDto MapToDto (Employee employee){
         EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);
         return dto;
     }
     Employee MapToEntity (EmployeeDto dto) {
         Employee emp = modelMapper.map(dto, Employee.class);
         return emp;
     }

    public void deleteEmployee(long id) {
         employeeRepository.deleteById(id);
    }


    public EmployeeDto updateEmployee(long id, EmployeeDto dto) {
        Employee employee = MapToEntity(dto);
        employee.setId(id);
        Employee updateEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto = MapToDto(updateEmployee);
        return employeeDto;
    }

    public List<EmployeeDto> getEmployee(int pageNo, int pageSize, String sortBy, String sortDir) {
       Sort sort = sortDir.equalsIgnoreCase("asc")?
               Sort.by(sortBy).ascending(): Sort.by(sortDir).descending();
        Pageable page = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee> employees = all.getContent();
        List<EmployeeDto> dto = employees.stream().map(e -> MapToDto(e)).collect(Collectors.toList());
    return dto;
    }

    public EmployeeDto getEmployeeById(long empId) {
        Employee employee = employeeRepository.findById(
                empId).orElseThrow(() -> new ResourceNotFound("Resource not found with id: " + empId));
        EmployeeDto dto = MapToDto(employee);
        return dto;
    }
}
