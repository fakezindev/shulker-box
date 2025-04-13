package com.shulkerbox.service;

import com.shulkerbox.model.Supplier;
import com.shulkerbox.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired // Injeta automaticamente uma instância de FornecedorRepository.
    private SupplierRepository supplierRepository;

    /**
     * Retorna uma lista de todos os fornecedores.
     */
    public List<Supplier> listAll() {
        return supplierRepository.findAll();
    }

    /**
     * Busca um fornecedor pelo "ID".
     * Retorna o fornecedor ou null se não encontrado.
     * @deprecated Prefira usar findByIdOptional() para melhor tratamento de valores nulos
     */
    @Deprecated
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    /**
     * Busca um fornecedor pelo "ID" retornando um Optional.
     * Método preferido para evitar NullPointerException.
     */
    public Optional<Supplier> findByIdOptional(Long id) {
        return supplierRepository.findById(id);
    }

    /**
     * Salva um novo fornecedor ou atualiza um existente.
     */
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    /**
     * Exclui um fornecedor pelo ID.
     */
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    /**
     * Verifica se um fornecedor existe pelo ID.
     */
    public boolean existsById(Long id) {
        return supplierRepository.existsById(id);
    }
}