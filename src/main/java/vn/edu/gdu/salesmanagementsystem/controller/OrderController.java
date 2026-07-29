package vn.edu.gdu.salesmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.gdu.salesmanagementsystem.dto.CreateOrderRequest;
import vn.edu.gdu.salesmanagementsystem.entity.Order;
import vn.edu.gdu.salesmanagementsystem.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. API Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        try {
            // Gọi Service xử lý nghiệp vụ
            Order createdOrder = orderService.createOrder(orderRequest);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Trả về lỗi nếu thiếu hàng trong kho hoặc ID không hợp lệ
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}