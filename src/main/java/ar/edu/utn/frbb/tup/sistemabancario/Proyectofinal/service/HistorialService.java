package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.HistorialDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HistorialService {
    @Autowired
    private CuentaDao cuentaDao;

    public HistorialDto consultarHistorial(long cuentaId) throws CuentaDoesntExistException {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(cuentaId);
        if (cuenta == null) {
            throw new CuentaDoesntExistException("La cuenta no existe");
        }
        return obtenerHistorial(cuenta);

    }

    private HistorialDto obtenerHistorial(Cuenta cuenta) {

/*        Set<Movimientos> transacciones = cuenta.getMovimientos().stream()
                .map(movimiento -> new Movimientos(
                        movimiento.getMonto(),
                        movimiento.getDescripcion(),
                        movimiento.getNumeroTransaccion(),
                        movimiento.getFecha(),
                        movimiento.getMovimiento()
                        ))
                .collect(Collectors.toSet());
*/
        HistorialDto respuestaHistorial = new HistorialDto();
        respuestaHistorial.setNumeroCuenta(cuenta.getNumeroCuenta());
        respuestaHistorial.setHistorial(cuenta.getMovimientos());

        return respuestaHistorial;

    }


}
