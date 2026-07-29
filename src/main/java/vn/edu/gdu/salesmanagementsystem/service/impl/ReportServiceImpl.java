package vn.edu.gdu.salesmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import vn.edu.gdu.salesmanagementsystem.service.ReportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public Map<String, Object> getOverviewReport() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalOrders", 42L);
        map.put("totalRevenue", 25500000.0);
        return map;
    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts() {
        return List.of(
                new TopSellingProductDTO(1L, "Ao Thun Form Rong", 85L, 12750000.0),
                new TopSellingProductDTO(2L, "Quan Jean Slimfit", 42L, 10500000.0),
                new TopSellingProductDTO(3L, "Giay Sneaker White", 15L, 2250000.0)
        );
    }
}