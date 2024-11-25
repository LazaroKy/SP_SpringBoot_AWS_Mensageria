package com.pblazaro.msdeposito.domain;


import com.pblazaro.msdeposito.domain.model.StatusDeposito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
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


}
