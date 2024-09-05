package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto;

public class OperacionDto {

    private long cuenta;
    private double monto;
    private String moneda;

    public long getCuenta() {
        return cuenta;
    }

    public void setCuenta(long cuenta) {
        this.cuenta = cuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
