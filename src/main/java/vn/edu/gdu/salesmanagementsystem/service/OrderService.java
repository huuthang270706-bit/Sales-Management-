package vn.edu.gdu.salesmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.gdu.salesmanagementsystem.dto.CreateOrderRequest;
import vn.edu.gdu.salesmanagementsystem.dto.OrderItemRequest;
import vn.edu.gdu.salesmanagementsystem.entity.Customer;
import vn.edu.gdu.salesmanagementsystem.entity.Order;
import vn.edu.gdu.salesmanagementsystem.entity.OrderDetail;
import vn.edu.gdu.salesmanagementsystem.entity.Product;
import vn.edu.gdu.salesmanagementsystem.repository.CustomerRepository;
import vn.edu.gdu.salesmanagementsystem.repository.OrderRepository;
import vn.edu.gdu.salesmanagementsystem.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. Logic Tạo Đơn Hàng
    @Transactional
    public Order createOrder(CreateOrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng ID: " + orderRequest.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        double totalAmount = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderItemRequest item : orderRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + item.getProductId()));

            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ số lượng trong kho!");
            }

            // Trừ tồn kho
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // Chi tiết đơn hàng
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(product.getPrice());

            orderDetails.add(detail);
            totalAmount += product.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order.setOrderDetails(orderDetails);

        return orderRepository.save(order);
    }

    // 2. Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 3. Lấy chi tiết 1 đơn hàng theo ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + id));
    }
}