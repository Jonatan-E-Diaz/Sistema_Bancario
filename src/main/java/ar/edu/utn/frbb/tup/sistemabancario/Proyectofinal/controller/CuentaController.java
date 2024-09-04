package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.ClienteDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaAlreadyExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.NotPosibleException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    @Autowired
    private CuentaValidator cuentaValidator;

    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    public Cuenta addCuenta(@RequestBody CuentaDto cuentaDto) throws InputErrorException, CuentaAlreadyExistException, NotPosibleException, ClienteDoesntExistException {
        cuentaValidator.validar(cuentaDto);
        return cuentaService.crearCuenta(cuentaDto);
    }

    @GetMapping("/{dni}")
    public Set<Cuenta> findCuentas(@PathVariable long dni) {
        return cuentaService.buscarCuentas(dni);
    }
}