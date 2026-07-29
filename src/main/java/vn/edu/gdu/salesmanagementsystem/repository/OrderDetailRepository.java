package vn.edu.gdu.salesmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import vn.edu.gdu.salesmanagementsystem.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT new vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO(" +
            "p.id, p.name, SUM(od.quantity), SUM(od.quantity * od.price)) " +
            "FROM OrderDetail od JOIN od.product p " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(od.quantity) DESC")
    List<TopSellingProductDTO> findTopSellingProducts();
}