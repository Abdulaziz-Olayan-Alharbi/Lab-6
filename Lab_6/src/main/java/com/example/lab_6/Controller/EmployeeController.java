package com.example.lab_6.Controller;

import com.example.lab_6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<Employee>();

    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @GetMapping("/get")
    public ResponseEntity getAllEmployees(){
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index,@Valid @RequestBody Employee employee , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        employees.set(index, employee);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index ){
        employees.remove(index);
        return ResponseEntity.status(HttpStatus.OK).body("Employee deleted successfully");
    }

    @GetMapping("search/position/{position}")
    public ResponseEntity getEmployeeByPosition(@PathVariable String position){
        ArrayList<Employee> positionEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().equals(position)) {
                positionEmployees.add(employee);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(positionEmployees);
    }

    @GetMapping("/search/age/{minAge}/{maxAge}")
    public ResponseEntity getAgeRange (@PathVariable int minAge,@PathVariable int maxAge){
        ArrayList<Employee> ageRangeEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge()<=maxAge && employee.getAge()>=minAge) {
                ageRangeEmployees.add(employee);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ageRangeEmployees);
    }

    @PutMapping("/leave")
    public ResponseEntity applyLeave(@Valid@RequestBody String id,Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        for (Employee employee : employees) {
            if (employee.getId().equals(id)&&employee.getAnnualLeave()>0){
                employee.setOnLeave(true);
                employee.setAnnualLeave(employee.getAnnualLeave()-1);
                return ResponseEntity.status(HttpStatus.OK).body(employee);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("not found");
    }

    @GetMapping("/noleave")
    public ResponseEntity getNoLeave(){
        ArrayList<Employee> noLeaveEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAnnualLeave()<=0){
                noLeaveEmployees.add(employee);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(noLeaveEmployees);
    }

    @PutMapping("/promote/{superviserID}/{id}")
    public ResponseEntity promote(@PathVariable String superviserID , @PathVariable String id){
        for (Employee superemployee : employees) {
            if (superemployee.getId().equals(superviserID) && superemployee.getPosition().equals("Superviser")){
                for (Employee employee : employees) {
                    if (employee.getId().equals(id)&&employee.getPosition().equals("Coordinator")&&employee.getAge()>=30&&employee.getOnLeave()==false) {
                        employee.setPosition("Supervisor");
                        return ResponseEntity.status(HttpStatus.OK).body(employees);
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body("employee not found");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("supervisor not found");
    }

}
