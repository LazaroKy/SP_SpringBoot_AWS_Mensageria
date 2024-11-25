package com.pblazaro.msextratobancario.infra.repository;

import com.pblazaro.msextratobancario.domain.model.Movimentacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacoes, Long> {
}
