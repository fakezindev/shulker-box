package com.shulkerbox.controller;

import com.shulkerbox.model.Supplier;
import com.shulkerbox.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.findById(id);
        return supplier == null ?
                ResponseEntity.ok(supplier) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Supplier> save(@RequestBody Supplier supplier) {
        Supplier savedSupplier = supplierService.save(supplier);
        return ResponseEntity.ok(savedSupplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Supplier> delete(@PathVariable Long id) {
        supplierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
