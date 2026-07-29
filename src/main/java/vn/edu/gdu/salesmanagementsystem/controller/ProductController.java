package vn.edu.gdu.salesmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.gdu.salesmanagementsystem.entity.Product;
import vn.edu.gdu.salesmanagementsystem.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Lấy danh sách tất cả sản phẩm (có hỗ trợ tìm kiếm qua ?keyword=...)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String keyword) {
        List<Product> products = productService.getAllProducts(keyword);
        return ResponseEntity.ok(products);
    }

    // 2. Lấy thông tin 1 sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Thêm sản phẩm mới
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // 4. Cập nhật thông tin sản phẩm (Sửa)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // 5. Xóa sản phẩm theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công!");
    }
}