package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Movimientos;
import java.util.HashSet;
import java.util.Set;

public class HistorialDto {

    private long numeroCuenta;
    private Set<Movimientos> historial;

    public HistorialDto(long numeroCuenta, Set<Movimientos> historial) {
        this.numeroCuenta = numeroCuenta;
        this.historial = historial;
    }

    public HistorialDto(){
        this.historial=new HashSet<>();
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Set<Movimientos> getHistorial() {
        return historial;
    }

    public void setHistorial(Set<Movimientos> historial) {
        this.historial = historial;
    }
}
