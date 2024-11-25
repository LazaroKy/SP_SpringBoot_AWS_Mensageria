package com.pblazaro.msextratobancario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movimentacoes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;
    private LocalDateTime diaMovimentacao = LocalDateTime.now();
    private BigDecimal saldoAnterior;
    private BigDecimal valorMovimentado;
    private String tipoTransacao;
    private BigDecimal saldoAtual;


}