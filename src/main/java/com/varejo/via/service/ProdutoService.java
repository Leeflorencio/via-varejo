package com.varejo.via.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varejo.via.config.RestTemplateConfig;
import com.varejo.via.dto.CondicaoPagamentoDto;
import com.varejo.via.dto.ParcelaDto;
import com.varejo.via.dto.ProdutoDto;
import com.varejo.via.model.ProdutoModel;
import com.varejo.via.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProdutoService implements ProdutoServiceImpl {

    @Autowired
    ProdutoRepository repository;

    @Autowired
    RestTemplateConfig restTemplate;

    @Override
    public ResponseEntity<Object> cadastrarProduto(ProdutoDto produto) {

        var produtoModel = new ProdutoModel();

        BeanUtils.copyProperties(produto, produtoModel);
        repository.save(produtoModel);

        return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso");
    }

    private static final String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json";

    public double getSelicAcumulada() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String url = String.format("%s&dataInicial=%s&dataFinal=%s",
                BASE_URL,
                startDate.format(formatter),
                endDate.format(formatter));

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, String>> data = objectMapper.readValue(response, new TypeReference<List<Map<String, String>>>() {});
            double accumulatedRate = data.stream()
                    .mapToDouble(entry -> Double.parseDouble(entry.get("valor")))
                    .sum();
            return accumulatedRate;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0115; // Taxa SELIC fixa de 1.15% ao mês como fallback
        }
    }

    public List<ParcelaDto> calcularParcelas(ProdutoDto produto, CondicaoPagamentoDto condicaoPagamento) {
        List<ParcelaDto> parcelas = new ArrayList<>();
        double selicMensal = 0.0115; // 1.15% ao mês
        if (condicaoPagamento.getQuantidadeParcelas() > 6) {
            selicMensal = getSelicAcumulada();
        }

        double valorFinanciado = produto.getValor() - condicaoPagamento.getValorEntrada();
        int qtdeParcelas = condicaoPagamento.getQuantidadeParcelas();

        for (int i = 1; i <= qtdeParcelas; i++) {
            double valorParcela = valorFinanciado / qtdeParcelas;
            if (qtdeParcelas > 6) {
                valorParcela += valorParcela * selicMensal;
            }
            parcelas.add(new ParcelaDto(i, valorParcela, qtdeParcelas > 6 ? selicMensal : 0));
        }

        return parcelas;
    }


}
