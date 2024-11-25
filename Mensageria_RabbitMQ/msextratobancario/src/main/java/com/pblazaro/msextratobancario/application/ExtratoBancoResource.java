package com.pblazaro.msextratobancario.application;

import com.pblazaro.msextratobancario.domain.ExtratoBancario;
import com.pblazaro.msextratobancario.infra.repository.ExtratoBancarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("extrato-bancario")
public class ExtratoBancoResource {

    private final ExtratoBancarioService extratoBancarioService;
    private final ExtratoBancarioRepository extratoBancarioRepository;

    public ExtratoBancoResource(ExtratoBancarioService extratoBancarioService, ExtratoBancarioRepository extratoBancarioRepository) {
        this.extratoBancarioService = extratoBancarioService;
        this.extratoBancarioRepository = extratoBancarioRepository;
    }


    @GetMapping
    public ResponseEntity<List<ExtratoBancario>> buscarExtratos() {
        List<ExtratoBancario> findall = extratoBancarioService.listarTodosExtratosBancarios();
        return ResponseEntity.ok().body(findall);
    }

    @PostMapping
    public ResponseEntity<ExtratoBancario> criarExtratoDeContaCliente(@RequestBody ExtratoBancario extratoBancario) {
        ExtratoBancario extrato = extratoBancarioService.salvarDadosExtratoBancario(extratoBancario);
        return ResponseEntity.status(HttpStatus.CREATED).body(extrato);
    }
}
