package com.pblazaro.msdeposito.domain.model;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter @Setter
public class DadosParaDeposito {
    @NotNull
    private Long idContaCliente;
    @NotNull
    private BigDecimal valorDeposito;

}

