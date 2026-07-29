package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.salesmanagementsystem.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}