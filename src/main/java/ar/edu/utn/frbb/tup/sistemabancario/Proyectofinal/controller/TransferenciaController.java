package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator.TransferenciaValidator;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferenciaController {

    @Autowired
    private TransferenciaValidator transferenciaValidator;

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping

    public RespuestaDto transferir(@RequestBody TransferenciaDto transferenciaDto) {

        transferenciaValidator.validarTransferencia(transferenciaDto);
        return transferenciaService.transferir(transferenciaDto);
    }
}
