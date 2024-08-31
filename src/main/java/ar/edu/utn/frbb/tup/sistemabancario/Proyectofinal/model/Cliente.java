package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model;
//Modelado de clientes

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.ClienteDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona {

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    @JsonBackReference
    private Set<Cuenta> cuentas = new HashSet<>();

    public Cliente() {}

    public Cliente(ClienteDto clienteDto) {
        super(clienteDto.getNombre(),clienteDto.getApellido(),clienteDto.getDni(),LocalDate.parse(clienteDto.getFechaNacimiento()));
        this.tipoPersona = TipoPersona.valueOf(clienteDto.getTipoPersona());
        this.banco = clienteDto.getBanco();
        this.fechaAlta = LocalDate.now();
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda moneda) {
        for (Cuenta cuenta:
                cuentas) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getTipoMoneda())) {
                return true;
            }
        }
        return false;
    }

    public void addCuenta(Cuenta cuenta) {
        if (cuentas == null) {
            cuentas = new HashSet<>();
        }
        cuentas.add(cuenta);
    }


}


