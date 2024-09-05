package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaValidator {

    @Autowired
    private Validator validaciones;

    public void validarTransferencia(TransferenciaDto transferenciaDto) throws InputErrorException {

        validaciones.validarNumeroCuenta(transferenciaDto.getCuentaOrigen());
        validaciones.validarNumeroCuenta(transferenciaDto.getCuentaDestino());
        validaciones.validarMonto(transferenciaDto.getMonto());
        validaciones.validarTipoMoneda(transferenciaDto.getMoneda());

    }
}