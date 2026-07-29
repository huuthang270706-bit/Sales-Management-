package vn.edu.gdu.salesmanagementsystem.service;
import java.util.List;
import vn.edu.gdu.salesmanagementsystem.dto.CreateOrderRequest;
import vn.edu.gdu.salesmanagementsystem.dto.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.gdu.salesmanagementsystem.entity.*;
import vn.edu.gdu.salesmanagementsystem.repository.*;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order createOrder(CreateOrderRequest request) { // <-- Dùng DTO ở đây
        // 1. Kiểm tra Customer
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Khách hàng có ID: " + request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        double totalAmount = 0.0;

        // 2. Duyệt qua từng sản phẩm từ DTO
        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Sản phẩm có ID: " + item.getProductId()));

            // Kiểm tra số lượng tồn kho
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm '" + product.getName() + "' không đủ số lượng trong kho! (Còn: " + product.getQuantity() + ")");
            }

            // Trừ số lượng tồn kho
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // Tạo OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(product.getPrice());

            totalAmount += product.getPrice() * item.getQuantity();

            // Thêm vào Order
            order.addOrderDetail(orderDetail);
        }

        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
}