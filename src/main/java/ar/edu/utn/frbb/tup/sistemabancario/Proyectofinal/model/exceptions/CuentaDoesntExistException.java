package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions;

public class CuentaDoesntExistException extends Throwable{
    public CuentaDoesntExistException(String message) {
        super(message);
    }
}
