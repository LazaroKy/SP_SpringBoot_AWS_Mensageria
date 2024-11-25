package com.pblazaro.msextratobancario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DepositoBanco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeposito;
    @Enumerated(EnumType.STRING)
    private StatusDeposito status;
    private Long idContaCliente;
    private BigDecimal valorDeposito;
    private String tipoTransacao = "Deposito";

    public enum StatusDeposito {DEPOSITADO, ERRO}

}
