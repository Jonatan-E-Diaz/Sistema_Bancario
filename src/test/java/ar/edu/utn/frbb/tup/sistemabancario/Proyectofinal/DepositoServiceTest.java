package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.OperacionDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMoneda;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMovimiento;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.CuentaService;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.DepositoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DepositoServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private Movimientos movimientos;

    @InjectMocks
    private DepositoService depositoService;

    private OperacionDto operacionDto;
    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        operacionDto = new OperacionDto();
        operacionDto.setCuenta(12345678);
        operacionDto.setMonto(1000);
        operacionDto.setMoneda("PESOS");

        cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345678);
        cuenta.setTipoMoneda(TipoMoneda.PESOS);
    }

    @Test
    public void testRealizarDepositoCuentaNoExiste() {
        when(cuentaDao.findByNumeroCuenta(operacionDto.getCuenta())).thenReturn(null);

        RespuestaDto respuesta = depositoService.realizarDeposito(operacionDto);

        assertEquals("La cuenta NO existe", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
    }

    @Test
    public void testRealizarDepositoMonedaDiferente() {
        cuenta.setTipoMoneda(TipoMoneda.DOLARES);  // Cuenta en dólares
        when(cuentaDao.findByNumeroCuenta(operacionDto.getCuenta())).thenReturn(cuenta);

        RespuestaDto respuesta = depositoService.realizarDeposito(operacionDto);

        assertEquals("Las cuentas son de diferente tipo", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
    }

    @Test
    public void testRealizarDepositoExito() {
        when(cuentaDao.findByNumeroCuenta(operacionDto.getCuenta())).thenReturn(cuenta);

        RespuestaDto respuesta = depositoService.realizarDeposito(operacionDto);

        assertEquals("Deposito realizado", respuesta.getMensaje());
        assertEquals("Exitoso", respuesta.getEstado());
        verify(cuentaService, times(1)).actualizarSaldo(cuenta, operacionDto.getMonto());
        verify(movimientos, times(1)).save(eq(cuenta), any(), eq(TipoMovimiento.DEPOSITO), anyString());
    }
}
