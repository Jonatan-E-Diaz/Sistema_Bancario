package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model;

import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.LocalDate;
@Component
public class Movimientos {


    private long numeroTransaccion;
    private double monto;
    private String descripcion;
    private LocalDate fecha;
    private TipoMovimiento movimiento;

    public Movimientos(double monto, String descripcion, long numeroTransaccion, LocalDate fecha, TipoMovimiento movimiento) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.numeroTransaccion = numeroTransaccion;
        this.fecha = fecha;
        this.movimiento = movimiento;
    }

    public Movimientos() {
    }

    public long getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(long numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoMovimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(TipoMovimiento movimiento) {
        this.movimiento = movimiento;
    }

    public void save(Cuenta cuenta,Object operacion, TipoMovimiento movimiento, String descripcion) {
        Movimientos movimientos = new Movimientos();
        if (movimiento == TipoMovimiento.DEPOSITO) {
            Operacion deposito = (Operacion) operacion;
            movimientos.setNumeroTransaccion(deposito.getNumeroTransaccion());
            movimientos.setMonto(deposito.getMonto());
            movimientos.setFecha(deposito.getFecha());
            movimientos.setDescripcion(descripcion);
            movimientos.setMovimiento(movimiento);
        } else if (movimiento == TipoMovimiento.RETIRO) {
            Operacion retiro = (Operacion) operacion;
            movimientos.setNumeroTransaccion(retiro.getNumeroTransaccion());
            movimientos.setMonto(retiro.getMonto());
            movimientos.setFecha(retiro.getFecha());
            movimientos.setDescripcion(descripcion);
            movimientos.setMovimiento(movimiento);
        } else if (movimiento == TipoMovimiento.TRANSFERENCIA_ENTRADA) {
            Transferencia transferenciaentrada = (Transferencia) operacion;
            movimientos.setNumeroTransaccion(transferenciaentrada.getNumeroTransaccion());
            movimientos.setMonto(transferenciaentrada.getMonto());
            movimientos.setFecha(transferenciaentrada.getFecha());
            movimientos.setDescripcion(descripcion);
            movimientos.setMovimiento(movimiento);
        }
            else if (movimiento == TipoMovimiento.TRANSFERENCIA_SALIDA) {
                Transferencia transferenciasalida = (Transferencia) operacion;
                movimientos.setNumeroTransaccion(transferenciasalida.getNumeroTransaccion());
                movimientos.setMonto(transferenciasalida.getMonto());
                movimientos.setFecha(transferenciasalida.getFecha());
                movimientos.setDescripcion(descripcion);
                movimientos.setMovimiento(movimiento);
            }
        cuenta.agregarHistorial(movimientos);
    }
}
