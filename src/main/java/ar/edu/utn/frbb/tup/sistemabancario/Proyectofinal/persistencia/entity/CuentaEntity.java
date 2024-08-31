package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.entity;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoCuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMoneda;

import java.time.LocalDate;

public class CuentaEntity extends BaseEntity {

    private long numeroCuenta;
    private LocalDate fechaApertura;
    private double saldo;
    private TipoCuenta tipoCuenta;
    private TipoMoneda tipoMoneda;
    private Cliente titular;
    //private Set<Movimientos> movimientos;

    public CuentaEntity(Cuenta cuenta) {
        super(cuenta.getNumeroCuenta());
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.fechaApertura = cuenta.getFechaApertura();
        this.saldo = cuenta.getSaldo();
        this.tipoCuenta = cuenta.getTipoCuenta();
        this.tipoMoneda = cuenta.getTipoMoneda();
        this.titular = cuenta.getTitular();
        //this.movimientos = cuenta.getMovimientos();
    }
    public Cuenta toCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(this.numeroCuenta);
        cuenta.setFechaApertura(this.fechaApertura);
        cuenta.setSaldo(this.saldo);
        cuenta.setTipoCuenta(this.tipoCuenta);
        cuenta.setTipoMoneda(this.tipoMoneda);
        cuenta.setTitular(this.titular);
        //cuenta.setMovimientos(this.movimientos);
        return cuenta;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public Cliente getTitular() {
        return titular;
    }
}
