package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.entity;

public class BaseEntity {

    private final long Id;

    public BaseEntity(long id) {
        Id=id;
    }
    public long getId() {
        return Id;
    }
}
