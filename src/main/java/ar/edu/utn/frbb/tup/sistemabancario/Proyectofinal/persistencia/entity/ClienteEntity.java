package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.entity;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoPersona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClienteEntity extends BaseEntity{

    private final long dni;
    private final TipoPersona tipoPersona;
    private final String nombre;
    private final String apellido;
    private final LocalDate fechaNacimiento;
    private final LocalDate fechaAlta;
    private final String banco;
    private final List<Cuenta> cuentas;


    public ClienteEntity(Cliente cliente) {
        super(cliente.getDni());
        this.dni = cliente.getDni();
        this.tipoPersona = cliente.getTipoPersona();
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.fechaNacimiento = cliente.getFechaNacimiento();
        this.fechaAlta = cliente.getFechaAlta();
        this.banco = cliente.getBanco();
        this.cuentas=new ArrayList<>();
        if (cliente.getCuentas() != null) {
            this.cuentas.addAll(cliente.getCuentas());
        }
    }

    public Cliente toCliente(){

        Cliente cliente = new Cliente();
        cliente.setDni(this.getId());
        cliente.setTipoPersona(this.tipoPersona);
        cliente.setNombre(this.nombre);
        cliente.setApellido(this.apellido);
        cliente.setFechaNacimiento(this.fechaNacimiento);
        cliente.setFechaAlta(this.fechaAlta);
        cliente.setBanco(this.banco);
        cliente.setCuentas(new HashSet<>(cuentas));
        return cliente;
    }

    public long getDni() {
        return dni;
    }


}
