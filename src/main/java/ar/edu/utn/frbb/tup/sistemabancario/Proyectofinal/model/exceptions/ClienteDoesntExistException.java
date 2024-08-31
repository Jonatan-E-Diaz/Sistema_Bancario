package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions;

public class ClienteDoesntExistException extends Throwable{
    public ClienteDoesntExistException(String message) {
        super(message);
    }
}
