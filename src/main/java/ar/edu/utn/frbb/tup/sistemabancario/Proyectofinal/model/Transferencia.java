package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model;

import java.time.LocalDate;
import java.util.Random;

public class Transferencia {

    private long numeroTransaccion;
    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private TipoMoneda moneda;
    private LocalDate fecha;

    public Transferencia() {
        this.numeroTransaccion = new Random().nextLong(9999) + 1000;
        this.fecha = LocalDate.now();
    }

    public long getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(long numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
