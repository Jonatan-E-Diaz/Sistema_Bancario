package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.OperacionDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperacionValidator {

    @Autowired
    private Validator validaciones;

    public void validarOperacion(OperacionDto operacionDto) throws InputErrorException{

        validaciones.validarNumeroCuenta(operacionDto.getCuenta());
        validaciones.validarMonto(operacionDto.getMonto());
        validaciones.validarTipoMoneda(operacionDto.getMoneda());
    }
}
