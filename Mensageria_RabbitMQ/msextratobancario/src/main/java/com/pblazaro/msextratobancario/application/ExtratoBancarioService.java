package com.pblazaro.msextratobancario.application;

import com.pblazaro.msextratobancario.domain.ExtratoBancario;

import com.pblazaro.msextratobancario.infra.repository.ExtratoBancarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExtratoBancarioService {
    private final ExtratoBancarioRepository extratoBancarioRepository;

    public ExtratoBancarioService(ExtratoBancarioRepository extratoBancarioRepository) {
        this.extratoBancarioRepository = extratoBancarioRepository;
    }

    public ExtratoBancario salvarDadosExtratoBancario(ExtratoBancario extratoBancario) {
        extratoBancario.setDataExpedicao(LocalDateTime.now());
        return extratoBancarioRepository.save(extratoBancario);
    }

    public List<ExtratoBancario> listarTodosExtratosBancarios() {
        return extratoBancarioRepository.findAll();
    }
}
