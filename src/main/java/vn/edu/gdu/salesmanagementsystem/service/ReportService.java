package vn.edu.gdu.salesmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import vn.edu.gdu.salesmanagementsystem.repository.OrderDetailRepository;
import vn.edu.gdu.salesmanagementsystem.repository.OrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Map<String, Object> getOverviewReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("totalOrders", orderRepository.count());
        report.put("totalRevenue", orderRepository.getTotalRevenue());
        return report;
    }

    public List<TopSellingProductDTO> getTopSellingProducts() {
        return orderDetailRepository.findTopSellingProducts();
    }
}