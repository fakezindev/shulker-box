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
     * Retorna null se o fornecedor não for encontrado.
     */
    public Supplier findById(Long id){
        Optional<Supplier> supplier =   supplierRepository.findById(id);
        return supplier.orElse(null);
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
    public void deleteById(Long id){
        supplierRepository.deleteById(id);
    }
}
