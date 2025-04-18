package com.shulkerbox.service;

import com.shulkerbox.model.Product;
import com.shulkerbox.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para a entidade Produto.
 * Esta classe contém a lógica de negócio para manipulação de produtos.
 */
@Service // Indica que esta classe é um serviço gerenciado pelo Spring.
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retorna uma lista de todos os produtos.
     */
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    /**
     * Busca um produto pelo "ID".
     * Retorna null se o produto não for encontrado.
     */
    public Product searchById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null); // Retorna o produto ou null se não existir.
    }

    /**
     * Salva um novo produto ou atualiza um existente.
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Atualiza um produto existente.
     */
    public Product update(Long id, Product updatedProduct) {
        Product existingProduct = searchById(id);
        if(existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setQuantityStock(updatedProduct.getQuantityStock());
            existingProduct.setSupplier(updatedProduct.getSupplier());
            return productRepository.save(existingProduct);
        }
        return null; // Retorna null se o produto não existir.
    }

    /**
     * Exclui um produto pelo "ID".
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        productRepository.delete(product);
    }



    /**
     * Atualiza a quantidade em estoque de um produto.
     */
    public Product updateStock(Long id, Integer newQuantityStock) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        existingProduct.setQuantityStock(newQuantityStock);
        return productRepository.save(existingProduct);
    }
}
