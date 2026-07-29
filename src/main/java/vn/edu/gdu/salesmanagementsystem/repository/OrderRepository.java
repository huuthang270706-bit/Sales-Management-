package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.salesmanagementsystem.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository sẽ tự động cung cấp sẵn hàm save(), findById(), deleteById(),...
}