package com.shulkerbox.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor; // Adicionar se você vai usar @Builder ou construtor completo
import lombok.Builder; // Adicionar se você vai usar @Builder
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar se você vai usar @Builder ou construtor padrão

/**
 * Entidade que representa um fornecedor no sistema.
 * Esta classe é mapeada para a tabela "suppliers" no banco de dados.
 */
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode automaticamente.
@Entity // Indica que esta classe é uma entidade JPA.
@Builder // Adicionado para facilitar a criação de instâncias (útil em testes ou DTOs)
@NoArgsConstructor // Necessário com @Builder para JPA
@AllArgsConstructor // Necessário com @Builder para JPA
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

    /**
     * Email do fornecedor.
     * Este campo pode ser nulo, mas é boa prática ter validação de formato.
     */
    @Column // Por padrão, é nullable
    private String email; // NOVO CAMPO: Adicionado o email

}