package com.varejo.via.service;

import com.varejo.via.dto.ProdutoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProdutoServiceImpl {
    ResponseEntity<Object> cadastrarProduto(ProdutoDto produto);
}
