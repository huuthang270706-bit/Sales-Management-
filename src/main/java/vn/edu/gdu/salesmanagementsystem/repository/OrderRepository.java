package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.salesmanagementsystem.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Tính tổng doanh thu (nếu chưa có đơn hàng nào sẽ trả về 0)
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    Double getTotalRevenue();
}