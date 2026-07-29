package vn.edu.gdu.salesmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.gdu.salesmanagementsystem.entity.Product;
import vn.edu.gdu.salesmanagementsystem.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 1. Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Lấy thông tin sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 3. Thêm mới hoặc cập nhật sản phẩm
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 4. Xóa sản phẩm theo ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}