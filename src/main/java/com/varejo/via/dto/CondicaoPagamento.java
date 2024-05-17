package com.varejo.via.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondicaoPagamento {

    private BigDecimal valorEntrada;
    private Integer quantidadeParcelas;
}
