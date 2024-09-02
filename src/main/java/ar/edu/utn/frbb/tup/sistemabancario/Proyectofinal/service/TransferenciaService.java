package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Objects;

@Service
public class TransferenciaService {


    @Autowired
    private Movimientos movimientos;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private BanelcoService banelcoService;

    public RespuestaDto transferir(TransferenciaDto transferenciaDto) {

        Cuenta cuentaOrigen = cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen());

         if (cuentaOrigen == null) {
             return generarRespuesta("Cuenta origen no encontrada", "Fallido");
         }
         Cuenta cuentaDestino = cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino());
         if (cuentaDestino == null) {
            return generarRespuesta("Cuenta destino no encontrada", "Fallido");
        }
         if (cuentaOrigen.getTipoMoneda() != cuentaDestino.getTipoMoneda() && !Objects.equals(transferenciaDto.getMoneda(), String.valueOf(cuentaOrigen.getTipoMoneda()))) {
             return generarRespuesta("Las cuentas son de diferente tipo", "Fallido");
         }
         if (cuentaOrigen.getSaldo() < transferenciaDto.getMonto()) {
            return generarRespuesta("Saldo insuficiente", "Fallido");
        }
        if (!cuentaOrigen.getTitular().getBanco().equals(cuentaDestino.getTitular().getBanco())) {
            if (banelcoService.comprobarBanco(cuentaDestino.getTitular().getDni())){
                return realizarTransferencia(transferenciaDto, cuentaOrigen, cuentaDestino);
            }
            else {
                return generarRespuesta("El banco destino no es Banelco", "Fallido");
            }
        }
        return realizarTransferencia(transferenciaDto, cuentaOrigen, cuentaDestino);
    }

    private RespuestaDto generarRespuesta(String mensaje, String estado) {
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setMensaje(mensaje);
        respuesta.setEstado(estado);
        return respuesta;
    }

    private RespuestaDto realizarTransferencia(TransferenciaDto transferenciaDto, Cuenta cuentaOrigen, Cuenta cuentaDestino) {
        Transferencia transferencia = toTransferencia(transferenciaDto);

        double comision = calcularComision(transferenciaDto.getMonto(), cuentaOrigen.getTipoMoneda());

        cuentaService.actualizarSaldoTransferencia(cuentaOrigen,cuentaDestino, transferenciaDto.getMonto(), comision);
        movimientos.save(cuentaOrigen, transferencia, TipoMovimiento.TRANSFERENCIA_SALIDA, "TRANSFERENCIA REALIZADA A LA CUENTA " + cuentaDestino.getNumeroCuenta());
        movimientos.save(cuentaDestino, transferencia, TipoMovimiento.TRANSFERENCIA_ENTRADA, "TRANSFERENCIA REALIZADA A LA CUENTA " + cuentaDestino.getNumeroCuenta());

        cuentaDao.update(cuentaOrigen);
        cuentaDao.update(cuentaDestino);


        return generarRespuesta("Transferencia realizada", "Exitoso");

    }

    private double calcularComision(double monto, TipoMoneda moneda) {
        if (moneda == TipoMoneda.PESOS && monto > 1000000) {
            return monto * 0.02;
        } else if (moneda == TipoMoneda.DOLARES && monto > 5000) {
            return monto * 0.005;
        } else {
            return monto*0;
        }
    }

    private Transferencia toTransferencia(TransferenciaDto transferenciaDto) {
        Transferencia transferencia = new Transferencia();
        transferencia.setMonto(transferenciaDto.getMonto());
        transferencia.setMoneda(TipoMoneda.valueOf(transferenciaDto.getMoneda()));
        transferencia.setCuentaOrigen(transferenciaDto.getCuentaOrigen());
        transferencia.setCuentaDestino(transferenciaDto.getCuentaDestino());
        return transferencia;
    }
}