package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.OperacionDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator.OperacionValidator;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.RetiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retiro")

public class RetiroController {

    @Autowired
    private OperacionValidator operacionValidator;
    @Autowired
    private RetiroService retiroService;

    @PostMapping
    public RespuestaDto retirar(@RequestBody OperacionDto operacionDto) throws InputErrorException, InterruptedException {

        operacionValidator.validarOperacion(operacionDto);
        return retiroService.realizarRetiro(operacionDto);

    }


}
