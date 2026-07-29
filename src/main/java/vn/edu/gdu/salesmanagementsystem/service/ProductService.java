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

    // 1. Lấy danh sách sản phẩm (Có hỗ trợ lọc theo từ khóa tìm kiếm)
    public List<Product> getAllProducts(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(keyword.trim());
        }
        return productRepository.findAll();
    }
    // 2. Lấy thông tin sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 3. Thêm mới sản phẩm
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 4. Cập nhật thông tin sản phẩm
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có ID: " + id));

        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());

        return productRepository.save(existingProduct);
    }

    // 5. Xóa sản phẩm theo ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}