package com.pblazaro.msdeposito.infra.repository;

import com.pblazaro.msdeposito.domain.DepositoBanco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepositoRepository extends JpaRepository<DepositoBanco, Long> {
}
