package vn.edu.gdu.salesmanagementsystem.service;
import java.util.List;
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

    @Transactional // Đảm bảo nếu lỗi giữa chừng sẽ Rollback toàn bộ
    public Order createOrder(Order orderRequest) {
        // 1. Kiểm tra Customer có tồn tại không
        Customer customer = customerRepository.findById(orderRequest.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Khách hàng có ID: " + orderRequest.getCustomer().getId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        double totalAmount = 0.0;

        // 2. Duyệt qua từng sản phẩm trong đơn hàng
        for (OrderDetail detailRequest : orderRequest.getOrderDetails()) {
            Product product = productRepository.findById(detailRequest.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Sản phẩm có ID: " + detailRequest.getProduct().getId()));

            // Kiểm tra số lượng tồn kho
            if (product.getQuantity() < detailRequest.getQuantity()) {
                throw new RuntimeException("Sản phẩm '" + product.getName() + "' không đủ số lượng trong kho!");
            }

            // Trừ số lượng tồn kho
            product.setQuantity(product.getQuantity() - detailRequest.getQuantity());
            productRepository.save(product);

            // Tạo OrderDetail mới
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setPrice(product.getPrice()); // Lấy giá hiện tại của sản phẩm

            // Tính tổng tiền
            totalAmount += product.getPrice() * detailRequest.getQuantity();

            // Thêm vào danh sách của Order
            order.addOrderDetail(orderDetail);
        }

        order.setTotalAmount(totalAmount);

        // 3. Lưu Đơn hàng (Cascade.ALL sẽ tự động lưu các OrderDetail)
        return orderRepository.save(order);

    }
    // Lấy danh sách tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy chi tiết đơn hàng theo ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đơn hàng có ID: " + id));
    }

    // Xóa/Hủy đơn hàng (Hoàn trả số lượng kho)
    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);

        // Hoàn trả số lượng sản phẩm về lại kho
        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            product.setQuantity(product.getQuantity() + detail.getQuantity());
            productRepository.save(product);
        }

        orderRepository.delete(order);
    }
}