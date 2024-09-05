package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.OperacionDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Operacion;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMovimiento;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositoService {
    @Autowired
    private CuentaDao cuentaDao;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private Movimientos movimientos;

    public RespuestaDto realizarDeposito(OperacionDto depositoDto) {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(depositoDto.getCuenta());

        if (cuenta == null) {
            return generarRespuesta("La cuenta NO existe", "Fallido");
        }
        if (!depositoDto.getMoneda().equals(String.valueOf(cuenta.getTipoMoneda()))){
            return generarRespuesta("Las cuentas son de diferente tipo", "Fallido");
        }

        return  depositar(cuenta,depositoDto);
    }

    private RespuestaDto generarRespuesta(String mensaje, String estado) {
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setMensaje(mensaje);
        respuesta.setEstado(estado);
        return respuesta;
    }
    private RespuestaDto depositar(Cuenta cuenta, OperacionDto depositoDto) {
        cuentaService.actualizarSaldo(cuenta, depositoDto.getMonto());
        Operacion deposito = toDeposito(depositoDto);
        movimientos.save(cuenta, deposito, TipoMovimiento.DEPOSITO,"DEPOSITO REALIZADO A LA CUENTA " + cuenta.getNumeroCuenta());

        return generarRespuesta("Deposito realizado", "Exitoso");
    }
    private Operacion toDeposito(OperacionDto depositoDto) {
        Operacion deposito = new Operacion();
        deposito.setMonto(depositoDto.getMonto());
        deposito.setMoneda(depositoDto.getMoneda());
        deposito.setCuenta(depositoDto.getCuenta());
        return deposito;
    }
}
