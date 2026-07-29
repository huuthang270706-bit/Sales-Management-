package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.salesmanagementsystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}