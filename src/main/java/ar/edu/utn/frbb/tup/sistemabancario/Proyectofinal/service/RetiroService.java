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
public class RetiroService {

        @Autowired
        private CuentaDao cuentaDao;
        @Autowired
        private CuentaService cuentaService;
        @Autowired
        private Movimientos movimientos;

        public RespuestaDto realizarRetiro(OperacionDto retiroDto) {
            Cuenta cuenta = cuentaDao.findByNumeroCuenta(retiroDto.getCuenta());

            if (cuenta == null) {
                return generarRespuesta("La cuenta NO existe", "Fallido");
            }
            if (!retiroDto.getMoneda().equals(String.valueOf(cuenta.getTipoMoneda()))){
                return generarRespuesta("Las cuentas son de diferente tipo", "Fallido");
            }
            if (cuenta.getSaldo()<retiroDto.getMonto()) {
                return generarRespuesta("No tiene saldo suficiente", "Fallido");
            }

            return  retirar(cuenta,retiroDto);
        }

        private RespuestaDto generarRespuesta(String mensaje, String estado) {
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setMensaje(mensaje);
        respuesta.setEstado(estado);
        return respuesta;
        }

        private RespuestaDto retirar(Cuenta cuenta, OperacionDto retiroDto) {
        cuentaService.actualizarSaldoRetiro(cuenta, retiroDto.getMonto());
        Operacion deposito = toRetiro(retiroDto);
        movimientos.save(cuenta, deposito, TipoMovimiento.DEPOSITO,"RETIRO REALIZADO A LA CUENTA " + cuenta.getNumeroCuenta());

        return generarRespuesta("Retiro realizado", "Exitoso");
        }

        private Operacion toRetiro(OperacionDto retiroDto) {
        Operacion retiro = new Operacion();
        retiro.setMonto(retiroDto.getMonto());
        retiro.setMoneda(retiroDto.getMoneda());
        retiro.setCuenta(retiroDto.getCuenta());
        return retiro;
    }
}
