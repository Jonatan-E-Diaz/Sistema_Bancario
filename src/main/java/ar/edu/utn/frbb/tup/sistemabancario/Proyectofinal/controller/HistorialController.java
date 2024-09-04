package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.HistorialDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/cuenta")
public class HistorialController {
    @Autowired
    private HistorialService historialService;

    @GetMapping("/{cuentaId}/transacciones")
    public HistorialDto consultarHistorial(@PathVariable long cuentaId) throws CuentaDoesntExistException {
        return historialService.consultarHistorial(cuentaId);
    }
}
