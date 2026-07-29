package vn.edu.gdu.salesmanagementsystem.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.gdu.salesmanagementsystem.dto.TopSellingProductDTO;
import vn.edu.gdu.salesmanagementsystem.service.ReportService;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // GET http://localhost:8081/api/reports/overview
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview() {
        return ResponseEntity.ok(reportService.getOverviewReport());
    }

    // GET http://localhost:8081/api/reports/top-selling
    @GetMapping("/top-selling")
    public ResponseEntity<List<TopSellingProductDTO>> getTopSelling() {
        return ResponseEntity.ok(reportService.getTopSellingProducts());
    }
}