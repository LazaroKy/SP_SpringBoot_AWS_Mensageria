package com.pblazaro.msextratobancario.domain;

import com.pblazaro.msextratobancario.domain.model.Movimentacoes;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ExtratoBancario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExtrato;
    private Long idContaCliente;
    private LocalDateTime dataExpedicao = LocalDateTime.now();
    private BigDecimal saldo;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Movimentacoes> movimentacoes = new ArrayList<>();
}
