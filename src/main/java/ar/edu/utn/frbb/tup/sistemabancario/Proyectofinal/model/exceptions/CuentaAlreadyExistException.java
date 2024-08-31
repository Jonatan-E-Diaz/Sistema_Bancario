package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions;

public class CuentaAlreadyExistException extends Throwable{
    public CuentaAlreadyExistException(String message) {
        super(message);
    }
}
