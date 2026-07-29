package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.salesmanagementsystem.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}