package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.OperacionDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMoneda;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMovimiento;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.CuentaService;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.RetiroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RetiroServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private Movimientos movimientos;

    @InjectMocks
    private RetiroService retiroService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void realizarRetiro_exito() {
        OperacionDto operacionDto = new OperacionDto();
        operacionDto.setCuenta(12345L);
        operacionDto.setMonto(500);
        operacionDto.setMoneda("PESOS");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldo(1000);
        cuenta.setTipoMoneda(TipoMoneda.PESOS);

        when(cuentaDao.findByNumeroCuenta(anyLong())).thenReturn(cuenta);

        RespuestaDto respuesta = retiroService.realizarRetiro(operacionDto);

        assertEquals("Retiro realizado", respuesta.getMensaje());
        assertEquals("Exitoso", respuesta.getEstado());

        verify(cuentaService).actualizarSaldoRetiro(cuenta, 500);
        verify(movimientos).save(eq(cuenta), any(), eq(TipoMovimiento.DEPOSITO), anyString());
    }

    @Test
    public void realizarRetiro_cuentaNoExiste() {
        OperacionDto operacionDto = new OperacionDto();
        operacionDto.setCuenta(12345L);
        operacionDto.setMonto(500);
        operacionDto.setMoneda("PESOS");

        when(cuentaDao.findByNumeroCuenta(anyLong())).thenReturn(null);

        RespuestaDto respuesta = retiroService.realizarRetiro(operacionDto);

        assertEquals("La cuenta NO existe", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
    }

    @Test
    public void realizarRetiro_saldoInsuficiente() {
        OperacionDto operacionDto = new OperacionDto();
        operacionDto.setCuenta(12345L);
        operacionDto.setMonto(2000);
        operacionDto.setMoneda("PESOS");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldo(1000);
        cuenta.setTipoMoneda(TipoMoneda.PESOS);

        when(cuentaDao.findByNumeroCuenta(anyLong())).thenReturn(cuenta);

        RespuestaDto respuesta = retiroService.realizarRetiro(operacionDto);

        assertEquals("No tiene saldo suficiente", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
    }

    @Test
    public void realizarRetiro_diferenteTipoMoneda() {
        OperacionDto operacionDto = new OperacionDto();
        operacionDto.setCuenta(12345L);
        operacionDto.setMonto(500);
        operacionDto.setMoneda("DOLARES");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldo(1000);
        cuenta.setTipoMoneda(TipoMoneda.PESOS);

        when(cuentaDao.findByNumeroCuenta(anyLong())).thenReturn(cuenta);

        RespuestaDto respuesta = retiroService.realizarRetiro(operacionDto);

        assertEquals("Las cuentas son de diferente tipo", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
    }
}
