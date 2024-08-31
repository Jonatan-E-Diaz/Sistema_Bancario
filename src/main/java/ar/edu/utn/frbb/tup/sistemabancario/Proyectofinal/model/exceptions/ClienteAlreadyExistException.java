package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions;

public class ClienteAlreadyExistException extends Throwable {
    public ClienteAlreadyExistException(String message) {
        super(message);
    }
}
