package com.varejo.via.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.varejo.via.dto.CondicaoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table(name = "produto")
public class ProdutoModel {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    private String nome;

    private BigDecimal valor;

}
