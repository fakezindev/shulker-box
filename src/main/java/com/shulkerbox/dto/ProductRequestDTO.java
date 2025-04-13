package com.shulkerbox.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Integer quantityStock;
    private Long categoryId;  // Apenas o ID
    private Long supplierId;  // Apenas o ID
}
