package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import vn.edu.gdu.salesmanagementsystem.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 1. Đếm tổng số đơn hàng
    @Query("SELECT COUNT(o) FROM Order o")
    Long countTotalOrders();

    // 2. Tính tổng doanh thu toàn bộ hệ thống
    // COALESCE giúp trả về 0 nếu database chưa có đơn hàng nào (tránh bị NULL)
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0.0) FROM Order o")
    Double sumTotalRevenue();

    // 3. Truy vấn Top 5 sản phẩm bán chạy nhất (Gộp số lượng & Tổng doanh thu từng SP)
    // JPQL cho phép new trực tiếp vào Class DTO
    @Query("SELECT new vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO(" +
            "p.id, p.name, SUM(od.quantity), SUM(od.quantity * od.price)) " +
            "FROM OrderDetail od " +
            "JOIN od.product p " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(od.quantity) DESC")
    List<TopSellingProductDTO> findTopSellingProducts();
}