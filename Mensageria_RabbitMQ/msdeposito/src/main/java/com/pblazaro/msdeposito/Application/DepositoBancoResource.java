package com.pblazaro.msdeposito.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pblazaro.msdeposito.domain.DepositoBanco;
import com.pblazaro.msdeposito.domain.model.DadosParaDeposito;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deposito")
@Log4j2
public class DepositoBancoResource {
    @Autowired
    private DepositoBancoService depositoBancoService;

    @GetMapping
    public String status() {
        return "ON - Serviço ativo!";
    }

    @PostMapping
    public ResponseEntity<DepositoBanco> efetuarDepositoBancario(@RequestBody @Valid DadosParaDeposito dadosDeposito) {
        log.info("TRANSAÇÃO DE DEPOSITO INICIDA");
        DepositoBanco depositoBanco = depositoBancoService.efetuarDepositoBancario(dadosDeposito);
        return ResponseEntity.status(HttpStatus.CREATED).body(depositoBanco);

    }
}
