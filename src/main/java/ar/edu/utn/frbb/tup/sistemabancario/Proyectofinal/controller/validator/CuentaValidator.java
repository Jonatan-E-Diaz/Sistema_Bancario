package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuentaValidator {

    @Autowired
    private Validator validaciones;

    public void validar(CuentaDto cuentaDto) throws InputErrorException {

        validaciones.validarDni(cuentaDto.getDniCliente());
        validaciones.validarTipoMoneda(cuentaDto.getTipoMoneda());
        validaciones.validarTipoCuenta(cuentaDto.getTipoCuenta());
    }
}
