package com.varejo.via.controller;

import com.varejo.via.dto.ProdutoDto;
import com.varejo.via.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra-produto")
public class ProdutoController {

    @Autowired
    ProdutoService service;

    @PostMapping("/teste-cadastro")
    public ResponseEntity<Object> cadastrar(@RequestBody ProdutoDto produto){
        return service.cadastrarProduto(produto);
    }
}
