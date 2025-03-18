package com.shulkerbox.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa uma categoria no sistema.
 * Esta classe é mapeada para a tabela "categorias" no banco de dados.
 */
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode automaticamente.
@Entity // Indica que esta classe é uma entidade JPA.
@Table(name = "categories") // Define o nome da tabela no banco de dados.
public class Category {

    /**
     * Identificador único da categoria.
     * Este campo é a chave primária da tabela e é gerado automaticamente pelo banco de dados.
     */

    @Id // Indica que este campo é a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define que o valor será gerado automaticamente (auto-incremento).
    private Long id;

    /**
     * Nome da categoria.
     * Este campo não pode ser nulo no banco de dados.
     */

    @Column(nullable = false) // Define que a coluna no banco de dados não pode ser nula.
    private String name;

}
