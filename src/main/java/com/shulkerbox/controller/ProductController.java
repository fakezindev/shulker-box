package com.shulkerbox.controller;

import com.shulkerbox.dto.ProductRequestDTO;
import com.shulkerbox.dto.StockUpdateRequest;
import com.shulkerbox.model.Category;
import com.shulkerbox.model.Product;
import com.shulkerbox.model.Supplier;
import com.shulkerbox.repository.CategoryRepository;
import com.shulkerbox.service.ProductService;
import com.shulkerbox.service.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor // Lombok gera o construtor com as dependências
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.searchById(id);
        return product != null ?
                ResponseEntity.ok(product) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO dto) {
        try {
            // Verifica e obtém a categoria
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Categoria não encontrada com ID: " + dto.getCategoryId()));

            // Verifica e obtém o fornecedor
            Supplier supplier = supplierService.findByIdOptional(dto.getSupplierId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Fornecedor não encontrado com ID: " + dto.getSupplierId()));

            // Cria o produto
            Product product = Product.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .price(dto.getPrice())
                    .quantityStock(dto.getQuantityStock())
                    .category(category)
                    .supplier(supplier)
                    .build();

            return ResponseEntity.ok(productService.save(product));

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of(
                            "error", e.getReason(),
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.update(id, product);
        return updatedProduct != null ?
                ResponseEntity.ok(updatedProduct) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestBody StockUpdateRequest request) {
        Product product = productService.updateStock(id, request.getQuantityStock());
        return product != null ?
                ResponseEntity.ok(product) :
                ResponseEntity.notFound().build();
    }


}