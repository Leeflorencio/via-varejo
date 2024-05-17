package com.varejo.via.service;

import com.varejo.via.dto.ProdutoDto;
import com.varejo.via.model.ProdutoModel;
import com.varejo.via.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService implements ProdutoServiceImpl {

    @Autowired
    ProdutoRepository repository;

    @Override
    public ResponseEntity<Object> cadastrarProduto(ProdutoDto produto) {

        var produtoModel = new ProdutoModel();

        BeanUtils.copyProperties(produto, produtoModel);
        repository.save(produtoModel);

        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }
}
