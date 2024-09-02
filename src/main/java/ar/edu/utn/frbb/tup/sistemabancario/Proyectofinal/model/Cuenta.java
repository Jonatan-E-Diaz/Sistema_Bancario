package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cuenta {

    private long numeroCuenta;
    private LocalDate fechaApertura;
    private double saldo;
    private TipoCuenta tipoCuenta;
    private TipoMoneda tipoMoneda;
    @JsonManagedReference
    private Cliente titular;
    //@JsonIgnore
    private Set<Movimientos> movimientos;


    public Cuenta() {
        Random random = new Random();
        this.numeroCuenta = generarNumeroCuenta(random);
        this.fechaApertura = LocalDate.now();
        this.saldo = 0;
        this.movimientos = new HashSet<>();
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public Set<Movimientos> getMovimientos() {
        return movimientos;
    }

    public void agregarHistorial(Movimientos movimientos) {
        this.movimientos.add(movimientos);
    }

    private long generarNumeroCuenta(Random random) {
        return (long) random.nextInt(100000000);
    }

}
