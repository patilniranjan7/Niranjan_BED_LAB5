package com.greatlearning.ems.Controller;


import com.greatlearning.ems.Model.Employee;
import com.greatlearning.ems.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        List<Employee> employeeList = (List<Employee>) this.employeeRepository.findAll();
        model.addAttribute("all_employees", employeeList);
        return "index";
    }

    @GetMapping("/employees/new")
    public String createEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("new_employee", employee);
        return "create";
    }

    @GetMapping("/employees/edit/{id}")
    public String editEmployee(Model model, @PathVariable("id") Integer id) {
        Optional<Employee> editOptionalEmployee = this.employeeRepository.findById(id);
        Employee employee = editOptionalEmployee.get();
        model.addAttribute("edit_employee", employee);
        return "edit";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(Model model, @PathVariable("id") Integer id) {
        Optional<Employee> deleteOptionalEmployee = this.employeeRepository.findById(id);
        Employee deletedEmployee = deleteOptionalEmployee.get();
        this.employeeRepository.delete(deletedEmployee);
        return "redirect:/employees";
    }


    @PostMapping("/employees/new")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        this.employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/employees/{id}")
    public String updateEmployee(@PathVariable Integer id, @ModelAttribute("employee") Employee employee, Model model) {
        Optional<Employee> editOptionalEmployee = this.employeeRepository.findById(id);
        Employee oldEmployee = editOptionalEmployee.get();
        if(employee.getFirstName() != null)
            oldEmployee.setFirstName(employee.getFirstName());
        if(employee.getLastName() != null)
            oldEmployee.setLastName(employee.getLastName());
        if(employee.getEmailId() != null)
            oldEmployee.setEmailId(employee.getEmailId());
        Employee updatedEmployee = oldEmployee;
        this.employeeRepository.save(updatedEmployee);
        return "redirect:/employees";
    }

}
