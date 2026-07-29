package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.salesmanagementsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}