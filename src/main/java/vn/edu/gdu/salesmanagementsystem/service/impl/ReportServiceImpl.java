package vn.edu.gdu.salesmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import vn.edu.gdu.salesmanagementsystem.repository.OrderRepository;
import vn.edu.gdu.salesmanagementsystem.service.ReportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Map<String, Object> getOverviewReport() {
        Map<String, Object> map = new HashMap<>();

        // Gọi dữ liệu thật từ MySQL thông qua Repository
        Long totalOrders = orderRepository.countTotalOrders();
        Double totalRevenue = orderRepository.sumTotalRevenue();

        map.put("totalOrders", totalOrders);
        map.put("totalRevenue", totalRevenue);

        return map;
    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts() {
        // Truy vấn danh sách Top bán chạy thực tế từ MySQL
        return orderRepository.findTopSellingProducts();
    }
}