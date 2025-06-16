package com.shulkerbox.controller;

import com.shulkerbox.model.Supplier;
import com.shulkerbox.service.SupplierService;

// Importações do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Manter para HttpStatus.CREATED, etc.
import org.springframework.http.HttpStatusCode; // <-- NOVA IMPORTAÇÃO E TIPO PARA A VARIÁVEL 'status'
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// Importações do Jakarta Persistence (JPA)
import jakarta.persistence.EntityNotFoundException;

// Importações do Java Utilities
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "http://localhost:3000")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> listAll() {
        return supplierService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> findById(@PathVariable Long id) {
        return supplierService.findByIdOptional(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Supplier supplier) {
        try {
            if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do fornecedor é obrigatório.");
            }
            if (supplier.getCnpj() == null || supplier.getCnpj().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CNPJ do fornecedor é obrigatório.");
            }
            if (supplier.getTelefone() == null || supplier.getTelefone().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O telefone do fornecedor é obrigatório.");
            }

            Supplier savedSupplier = supplierService.save(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier);
        } catch (Exception e) {
            // -- CORREÇÃO PARA O ERRO 'Incompatible types' AQUI --
            HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR; // Declarar como HttpStatusCode
            String message = "Erro interno ao salvar fornecedor.";

            if (e instanceof ResponseStatusException) {
                ResponseStatusException rse = (ResponseStatusException) e;
                status = rse.getStatusCode(); // rse.getStatusCode() retorna HttpStatusCode
                message = rse.getReason();
            } else if (e.getMessage() != null &&
                    (e.getMessage().contains("ConstraintViolationException") || e.getMessage().contains("DataIntegrityViolationException"))) {
                status = HttpStatus.CONFLICT;
                message = "Já existe um fornecedor com este CNPJ ou dados inválidos.";
            }

            return ResponseEntity.status(status)
                    .body(Map.of(
                            "error", message,
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Supplier supplier) {
        try {
            if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do fornecedor é obrigatório para atualização.");
            }
            if (supplier.getCnpj() == null || supplier.getCnpj().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CNPJ do fornecedor é obrigatório para atualização.");
            }
            if (supplier.getTelefone() == null || supplier.getTelefone().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O telefone do fornecedor é obrigatório para atualização.");
            }

            Supplier updatedSupplier = supplierService.update(id, supplier);
            return ResponseEntity.ok(updatedSupplier);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // -- CORREÇÃO PARA O ERRO 'Incompatible types' AQUI --
            HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR; // Declarar como HttpStatusCode
            String message = "Erro interno ao atualizar fornecedor.";

            if (e instanceof ResponseStatusException) {
                ResponseStatusException rse = (ResponseStatusException) e;
                status = rse.getStatusCode(); // rse.getStatusCode() retorna HttpStatusCode
                message = rse.getReason();
            } else if (e.getMessage() != null &&
                    (e.getMessage().contains("ConstraintViolationException") || e.getMessage().contains("DataIntegrityViolationException"))) {
                status = HttpStatus.CONFLICT;
                message = "Erro de dados: CNPJ duplicado ou campos inválidos.";
            }

            return ResponseEntity.status(status)
                    .body(Map.of(
                            "error", message,
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }

    @DeleteMapping("/{id}")
    // -- CORREÇÃO PARA O ERRO 'ResponseEntity<Void>' AQUI --
    public ResponseEntity<?> delete(@PathVariable Long id) { // Alterado de ResponseEntity<Void> para ResponseEntity<?>
        try {
            supplierService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            // -- CORREÇÃO PARA O ERRO 'Incompatible types' AQUI --
            HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR; // Declarar como HttpStatusCode
            String message = "Erro interno ao excluir fornecedor: " + e.getMessage();

            return ResponseEntity.status(status)
                    .body(Map.of(
                            "error", message,
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }
}