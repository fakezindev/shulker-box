package com.shulkerbox.service;

import com.shulkerbox.dto.ProductRequestDTO; // Importar o DTO
import com.shulkerbox.model.Category;
import com.shulkerbox.model.Product;
import com.shulkerbox.model.Supplier;
import com.shulkerbox.repository.CategoryRepository; // Importar
import com.shulkerbox.repository.ProductRepository;
import com.shulkerbox.repository.SupplierRepository; // Importar (Assumindo que você tem um)
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus; // Para ResponseStatusException
import org.springframework.web.server.ResponseStatusException; // Para ResponseStatusException

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired // NOVO: Injetar CategoryRepository
    private CategoryRepository categoryRepository;
    @Autowired // NOVO: Injetar SupplierRepository (ou SupplierService se ele retornar um Optional<Supplier>)
    private SupplierRepository supplierRepository;


    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product searchById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Atualiza um produto existente usando um DTO.
     */
    public Product update(Long id, ProductRequestDTO dto) { // AGORA ACEITA DTO
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        // Atualizar campos a partir do DTO
        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setQuantityStock(dto.getQuantityStock());

        // Lógica para atualizar Categoria (se o ID for diferente ou se for fornecido)
        if (dto.getCategoryId() != null &&
                (existingProduct.getCategory() == null || !dto.getCategoryId().equals(existingProduct.getCategory().getId()))) {
            Category newCategory = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Categoria não encontrada com ID: " + dto.getCategoryId()));
            existingProduct.setCategory(newCategory);
        }

        // Lógica para atualizar Fornecedor (se o ID for diferente ou se for fornecido)
        if (dto.getSupplierId() != null &&
                (existingProduct.getSupplier() == null || !dto.getSupplierId().equals(existingProduct.getSupplier().getId()))) {
            Supplier newSupplier = supplierRepository.findById(dto.getSupplierId()) // Usar supplierRepository
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Fornecedor não encontrado com ID: " + dto.getSupplierId()));
            existingProduct.setSupplier(newSupplier);
        }

        // existingProduct.setBrand(dto.getBrand()); // REMOVIDO: Não mais no DTO ou Model

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        productRepository.delete(product);
    }

    public Product updateStock(Long id, Integer newQuantityStock) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        existingProduct.setQuantityStock(newQuantityStock);
        return productRepository.save(existingProduct);
    }
}