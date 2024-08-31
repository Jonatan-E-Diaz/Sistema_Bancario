package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.ClienteDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.ClienteService;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.NotPosibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")

public class ClienteController {

    @Autowired
    private ClienteValidator clienteValidator;

    @Autowired
    private ClienteService clienteService;


    @PostMapping
    public Cliente addCliente(@RequestBody ClienteDto clienteDto) throws NotPosibleException, InputErrorException {

        clienteValidator.validar(clienteDto);

        return clienteService.darDeAltaCliente(clienteDto);

    }
    @GetMapping("/{dni}")
    public Cliente findCliente(@PathVariable long dni ){
        return clienteService.buscarCliente(dni);

    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<String> deleteCliente(@PathVariable long dni) throws NotPosibleException, ClienteDoesntExistException {
        clienteService.eliminarCliente(dni);
        return ResponseEntity.ok("Cliente con DNI " + dni + " ha sido eliminado exitosamente.");
    }

}
