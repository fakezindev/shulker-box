package com.shulkerbox.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa um fornecedor no sistema.
 * Esta classe é mapeada para a tabela "fornecedores" no banco de dados.
 */

@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode automaticamente.
@Entity // Indica que esta classe é uma entidade JPA.
@Table(name = "suppliers") // Define o nome da tabela no banco de dados.
public class Supplier {

    /**
     * Identificador único do fornecedor.
     * Este campo é a chave primária da tabela e é gerado automaticamente pelo banco de dados.
     */

    @Id // Indica que este campo é a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define que o valor será gerado automaticamente (auto-incremento).
    private Long id;

    /**
     * Nome do fornecedor.
     * Este campo não pode ser nulo no banco de dados.
     */

    @Column(nullable = false) // Define que a coluna no banco de dados não pode ser nula.
    private String name;

    /**
     * CNPJ do fornecedor.
     * Este campo não pode ser nulo e deve ser único no banco de dados.
     */

    @Column(nullable = false, unique = true) // Define que a coluna não pode ser nula e deve ser única.
    private String cnpj;

    /**
     * Telefone do fornecedor.
     * Este campo não pode ser nulo no banco de dados.
     */

    @Column(nullable = false) // Define que a coluna no banco de dados não pode ser nula.
    private String telefone;

}
