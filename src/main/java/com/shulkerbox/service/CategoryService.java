package com.shulkerbox.service;

import com.shulkerbox.model.Category;
import com.shulkerbox.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para a entidade Categoria.
 * Esta classe contém a lógica de negócio para manipulação de categorias.
 */
@Service // Indica que esta classe é um serviço gerenciado pelo Spring.
public class CategoryService {

    @Autowired // Injeta automaticamente uma instância de CategoryRepository.
    private CategoryRepository categoryRepository;

    /**
     * Retorna uma lista de todas as categorias.
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Busca uma categoria pelo "ID".
     * Retorna null se a categoria não for encontrada.
     */
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    /**
     * Salva uma nova categoria ou atualiza uma existente.
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    // Atualizar categoria existente
    public Category update(Long id, Category updatedCategory) {
        // Busca a categoria existente pelo ID
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    // Atualiza apenas o nome (ou outros campos se houver)
                    existingCategory.setName(updatedCategory.getName());
                    // Salva a categoria atualizada
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + id)); // Lança exceção se não encontrar
    }

    /**
     * Exclui uma categoria pelo ID.
     */
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
