package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto;

public class ClienteDto extends PersonaDto {

    private String tipoPersona;
    private String Banco;

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return Banco;
    }

    public void setBanco(String banco) {
        Banco = banco;
    }
}
