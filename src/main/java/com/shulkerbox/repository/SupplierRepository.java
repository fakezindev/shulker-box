package com.shulkerbox.repository;

import com.shulkerbox.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Produto.
 * Esta interface estende JpaRepository, fornecendo métodos CRUD básicos
 * e operações de acesso ao banco de dados para a entidade Produto.
 */

@Repository // Indica que esta interface é um repositório gerenciado pelo Spring.
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
