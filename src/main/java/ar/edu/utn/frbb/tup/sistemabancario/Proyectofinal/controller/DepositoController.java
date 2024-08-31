package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.OperacionDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator.OperacionValidator;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.DepositoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deposito")
public class DepositoController {

    @Autowired
    private OperacionValidator operacionValidator;
    @Autowired
    private DepositoService depositoService;

    @PostMapping

    public RespuestaDto depositar(@RequestBody OperacionDto operacionDto) {

        operacionValidator.validarOperacion(operacionDto);
        return depositoService.realizarDeposito(operacionDto);

    }

}
