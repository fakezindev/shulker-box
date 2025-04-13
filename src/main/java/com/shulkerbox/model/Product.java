package com.shulkerbox.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um produto no sistema.
 * Esta classe é mapeada para a tabela "produtos" no banco de dados.
 */
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode automaticamente.
@Entity // Indica que esta classe é uma entidade JPA.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products") // Define o nome da tabela no banco de dados.
public class Product {

    /**
     * Identificador único do produto.
     * Este campo é a chave primária da tabela e é gerado automaticamente pelo banco de dados.
     */
    @Id // Indica que este campo é a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define que o valor será auto-incremento.
    private Long id;

    /**
     * Nome do produto.
     * Este campo não pode ser nulo no banco de dados.
     */

    @Column(nullable = false)
    private String name;

    /**
     * Descrição do produto.
     * Este campo pode ser nulo e é mapeado como um campo TEXT no banco de dados.
     */
    @Column(columnDefinition = "TEXT") // Define o tipo da coluna como TEXT no banco de dados.
    private String description;

    @Column(nullable = false) // Define que a coluna no banco de dados não pode ser nula.
    private Double price;

    @ManyToOne // Define o relacionamento muitos-para-um.
    @JoinColumn(name = "category_id", nullable = false) // Define a coluna de relacionamento no banco de dados.
    private Category category;

    @Column(nullable = false) // Define que a coluna no banco de dados não pode ser nula.
    private Integer quantityStock;

    /**
     * Relacionamento muitos-para-um com a entidade Fornecedor.
     * Um produto é fornecido por um fornecedor.
     * A coluna "fornecedor_id" na tabela "produtos" será a chave estrangeira para a tabela "fornecedores".
     */

    @ManyToOne // Define o relacionamento muitos-para-um.
    @JoinColumn(name = "supplier_id", nullable = false) // Define a coluna de relacionamento no banco de dados.
    private Supplier supplier;

}



