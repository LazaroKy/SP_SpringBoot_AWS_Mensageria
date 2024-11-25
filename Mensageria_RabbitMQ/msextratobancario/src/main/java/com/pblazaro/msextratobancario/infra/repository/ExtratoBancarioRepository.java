package com.pblazaro.msextratobancario.infra.repository;

import com.pblazaro.msextratobancario.domain.ExtratoBancario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtratoBancarioRepository extends JpaRepository<ExtratoBancario, Long> {

    Optional<ExtratoBancario> findById(Long id);

    ExtratoBancario findExtratoBancarioByIdContaCliente(Long idContaCliente);
}
