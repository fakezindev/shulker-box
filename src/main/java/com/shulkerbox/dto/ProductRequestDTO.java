package com.shulkerbox.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private int quantityStock;
    private Long categoryId;
    private Long supplierId;
}
