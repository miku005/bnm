package com.bnm.controller;

import com.bnm.entity.Employee;
import com.bnm.payload.EmployeeDto;
import com.bnm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // http://localhost:8080/employee/add
@PostMapping("/add")
    public ResponseEntity<?> addEmployee(
        @Valid @RequestBody EmployeeDto dto, BindingResult result) {
    if (result.hasErrors()) {
        return new ResponseEntity<>(
                result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    EmployeeDto employeeDto = employeeService.addEmployee(dto);
    return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
}
    // http://localhost:8080/employee?id=2
@DeleteMapping
public ResponseEntity<String> deleteEmployee(@RequestParam long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
}
    // http://localhost:8080/employee?id=1
@PutMapping
public ResponseEntity<EmployeeDto> updateEmployee(
        @RequestParam long id,@RequestBody EmployeeDto dto){
    EmployeeDto employeeDto = employeeService.updateEmployee(id, dto);
    return new ResponseEntity<>(employeeDto, HttpStatus.OK);
}
    // http://localhost:8080/employee?PageNo=0&PageSize=3&SortBy=email&SortDir=asc
@GetMapping
public ResponseEntity<List<EmployeeDto>> getEmployees(
        @RequestParam(name = "PageNo",required = false,defaultValue = "0")int PageNo,
        @RequestParam(name = "PageSize",required = false,defaultValue = "10")int PageSize,
        @RequestParam(name = "SortBy",required = false, defaultValue = "name")String SortBy,
        @RequestParam(name = "SortDir",required = false, defaultValue = "asc")String SortDir

){
    List<EmployeeDto> employeeDto = employeeService.getEmployee(PageNo,PageSize,SortBy,SortDir);
    return new ResponseEntity<>(employeeDto, HttpStatus.OK);
}
    // http://localhost:8080/employee/employeeId/1
@GetMapping("/employeeId/{empId}")
public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long empId){
    EmployeeDto dto = employeeService.getEmployeeById(empId);
    return new ResponseEntity<>(dto, HttpStatus.OK);
}
}
