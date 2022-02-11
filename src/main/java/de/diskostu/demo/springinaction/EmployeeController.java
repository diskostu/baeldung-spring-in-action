package de.diskostu.demo.springinaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;


    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return repository.findAll();
    }


    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        repository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }


    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Long> deleteEmployee(@PathVariable Long id) {
        final Optional<Employee> byId = repository.findById(id);
        if (byId.isPresent()) {
            repository.delete(byId.get());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
