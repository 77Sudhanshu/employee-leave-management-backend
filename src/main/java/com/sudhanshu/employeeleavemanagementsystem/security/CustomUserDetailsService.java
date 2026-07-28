package com.sudhanshu.employeeleavemanagementsystem.security;

import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

                System.out.println("=================================");
                System.out.println("Email : " + employee.getEmail());
                System.out.println("Role from DB : " + employee.getRole());
                System.out.println("=================================");
                
                System.out.println("=================================");
System.out.println("Email : " + employee.getEmail());
System.out.println("Role from DB : " + employee.getRole());
System.out.println("=================================");

return User.builder()
        .username(employee.getEmail())
        .password(employee.getPassword())
        .roles(employee.getRole())
        .build();
    }
}