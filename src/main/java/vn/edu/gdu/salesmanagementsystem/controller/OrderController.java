package vn.edu.gdu.salesmanagementsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders") // Đường dẫn gốc cho tất cả API trong Controller này
public class OrderController {

    // 1. API Tạo đơn hàng mới (POST http://localhost:8081/api/orders)
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> orderData) {
        // Tạm thời trả về dữ liệu thành công để test Postman
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "Tạo đơn hàng thành công!");
        response.put("dataReceived", orderData);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. API Lấy danh sách đơn hàng (GET http://localhost:8081/api/orders)
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok("Danh sách đơn hàng sẽ hiển thị ở đây");
    }
}