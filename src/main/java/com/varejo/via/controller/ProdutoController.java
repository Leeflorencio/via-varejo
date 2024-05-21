package com.varejo.via.controller;

import com.varejo.via.dto.ParcelaDto;
import com.varejo.via.dto.ProdutoDto;
import com.varejo.via.dto.SimuladorDeCompra;
import com.varejo.via.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService service;

    @PostMapping("/cadastro")
    public ResponseEntity<Object> cadastrar(@RequestBody ProdutoDto produto){
        return service.cadastrarProduto(produto);
    }

    @PostMapping("/compra")
    public List<ParcelaDto> simularCompra(@RequestBody SimuladorDeCompra simulador) {
        return service.calcularParcelas(simulador.getProduto(), simulador.getCondicaoPagamento());
    }

}
