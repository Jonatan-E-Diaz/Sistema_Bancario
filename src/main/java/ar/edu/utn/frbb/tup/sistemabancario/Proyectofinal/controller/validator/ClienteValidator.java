package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidator {

    @Autowired
    private Validator validaciones;

    public void validar(ClienteDto clienteDto) throws InputErrorException {

        validaciones.validarDni(clienteDto.getDni());
        validaciones.validarString(clienteDto.getNombre(), "Nombre");
        validaciones.validarString(clienteDto.getApellido(), "Apellido");
        validaciones.validarFechaNacimiento(clienteDto.getFechaNacimiento());
        validaciones.validarTipoPersona(clienteDto.getTipoPersona());
        validaciones.validarString(clienteDto.getBanco(), "Banco");
    }
}

