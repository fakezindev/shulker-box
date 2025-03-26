package com.shulkerbox.controller;


import com.shulkerbox.model.Category;
import com.shulkerbox.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a categorias.
 * Mapeado para o caminho "/api/categorias".
 */
@RestController // Indica que esta classe é um controlador Spring MVC que processa requisições REST
@RequestMapping("/api/categories") // Todos os endpoints deste controlador começam com este caminho
public class CategoryController {

    @Autowired // Injeta automaticamente uma instância de CategoryService
    private CategoryService categoryService;

    /**
     * GET /api/categories
     * Recupera todas as categorias
     * @return Lista de todas as categorias com status HTTP 200
     */
    @GetMapping// Mapeia requisições HTTP GET para este metodo
    public List<Category> getAllCategories() {
        return categoryService.findAll(); // Delega para a camada de serviço
    }

    /**
     * GET /api/categories/{id}
     * Recupera uma categoria específica por ID
     * @param id O ID da categoria a ser recuperada
     * @return A categoria solicitada com HTTP 200, ou HTTP 404 se não encontrada
     */
    @GetMapping("/{id}") // Mapeia requisições GET com um ID na URL
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id); // Obtém a categoria do serviço
        return category != null ?
                ResponseEntity.ok(category) : // 200 OK se encontrada
                ResponseEntity.notFound().build(); // 404 Not Found se não existir
    }

    /**
     * POST /api/categories
     * Cria uma nova categoria
     * @param category O objeto categoria a ser criado (no corpo da requisição)
     * @return A categoria criada com status HTTP 200
     */
    @PostMapping // Mapeia requisições HTTP POST
    public ResponseEntity<Category> save(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category); // Salva via serviço
        return ResponseEntity.ok(savedCategory); // Retorna a categoria salva
    }

    /**
     * DELETE /api/categories/{id}
     * Remove uma categoria pelo ID
     * @param id O ID da categoria a ser removida
     * @return Resposta vazia com status HTTP 204 (No Content)
     */
    @DeleteMapping("/{id}") // Mapeia requisições HTTP DELETE
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id); // Remove via serviço
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
