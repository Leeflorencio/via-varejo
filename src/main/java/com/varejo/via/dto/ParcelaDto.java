package com.varejo.via.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaDto {

    private Integer numeroParcela;
    private double valor;
    private double taxaJurosAoMes;

    public ParcelaDto(int numeroParcela, double valor, double taxaJurosAoMes) {
        this.numeroParcela = numeroParcela;
        this.valor = valor;
        this.taxaJurosAoMes = taxaJurosAoMes;
    }
}
