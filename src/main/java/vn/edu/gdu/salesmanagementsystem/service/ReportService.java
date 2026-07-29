package vn.edu.gdu.salesmanagementsystem.service;

import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<String, Object> getOverviewReport();
    List<TopSellingProductDTO> getTopSellingProducts();
}