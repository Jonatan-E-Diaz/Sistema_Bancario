package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.validator;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.InputErrorException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class Validator {

        public void validarDni(long dni) throws InputErrorException {
            String dniString = String.valueOf(dni);

            if (!dniString.matches("\\d{8}")) {
                throw new InputErrorException("El DNI ingresado no es valido");
            }
        }

         public void validarString(String string, String campo) throws InputErrorException {

            if (string == null) {
                throw new InputErrorException("El campo " + campo + " ingresado no puede ser nulo");
            }


            if (string.isEmpty()) {
                throw new InputErrorException("El campo " + campo + " no puede estar vacío.");
            }


            if (!string.matches("[a-zA-Z\\s]+")) {
                throw new InputErrorException("El " + campo + " ingresado no es válido.");
            }

            if (string.length() < 2 || string.length() > 50) {
                throw new InputErrorException("El " + campo + " debe tener entre 2 y 50 caracteres.");
            }
        }

        public void validarTipoPersona(String tipoPersona) throws InputErrorException {

            if (tipoPersona == null) {
                throw new InputErrorException("El TIPO DE PERSONA no puede ser nulo.");
            }

            if (!"PERSONA_FISICA".equalsIgnoreCase(tipoPersona) && !"PERSONA_JURIDICA".equalsIgnoreCase(tipoPersona)) {
                throw new InputErrorException("El TIPO DE PERSONA ingresado no es válido.");
            }
        }

        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public void validarFechaNacimiento(String fechaNacimientoString){

            if (fechaNacimientoString == null) {
                throw new IllegalArgumentException("La FECHA DE NACIMIENTO no puede ser nula.");
            }

            try{
                LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoString, DATE_FORMATTER);
                LocalDate fechaActual = LocalDate.now();

            if (fechaNacimiento.isAfter(fechaActual)) {
                throw new InputErrorException("La FECHA DE NACIMIENTO ingresada no es valida.");
            }

            }  catch (DateTimeParseException | InputErrorException e) {
                throw new IllegalArgumentException("La FECHA DE NACIMIENTO ingresada no es valida.");
            }
        }

        public void validarTipoMoneda(String moneda) throws InputErrorException {
            if (!"PESOS".equalsIgnoreCase(moneda) && !"DOLARES".equalsIgnoreCase(moneda)) {
                throw new InputErrorException("El TIPO DE MONEDA ingresado no es valido.");
            }
        }

        public void validarTipoCuenta(String tipoCuenta) throws InputErrorException {
            if (!"CAJA_AHORRO".equalsIgnoreCase(tipoCuenta) && !"CUENTA_CORRIENTE".equalsIgnoreCase(tipoCuenta)) {
                throw new InputErrorException("El TIPO DE CUENTA ingresado no es valido.");
            }
        }

        public void validarNumeroCuenta(long numeroCuenta) throws InputErrorException {
            String nroCuentaString = String.valueOf(numeroCuenta);

            if (!nroCuentaString.matches("\\d{1,8}")) {
                throw new InputErrorException("El NUMERO DE CUENTA ingresado no es valido.");
            }
        }

        public void validarMonto(double monto) throws InputErrorException {
            if (monto <= 0) {
                throw new InputErrorException("El MONTO ingresado no es valido.");
            }
        }

}




