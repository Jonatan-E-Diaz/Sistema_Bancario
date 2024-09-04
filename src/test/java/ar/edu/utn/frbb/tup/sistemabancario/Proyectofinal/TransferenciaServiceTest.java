package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.RespuestaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.BanelcoService;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.CuentaService;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @InjectMocks
    private TransferenciaService transferenciaService;

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private Movimientos movimientos;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private BanelcoService banelcoService;

    private TransferenciaDto transferenciaDto;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Cliente titularOrigen;
    private Cliente titularDestino;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(123456);
        transferenciaDto.setCuentaDestino(654321);
        transferenciaDto.setMonto(5000);
        transferenciaDto.setMoneda("PESOS");

        titularOrigen = new Cliente();
        titularOrigen.setDni(12345678);
        titularOrigen.setBanco("BancoOrigen");

        titularDestino = new Cliente();
        titularDestino.setDni(87654321);
        titularDestino.setBanco("BancoDestino");

        cuentaOrigen = new Cuenta();
        cuentaOrigen.setNumeroCuenta(123456);
        cuentaOrigen.setSaldo(10000);
        cuentaOrigen.setTipoMoneda(TipoMoneda.PESOS);
        cuentaOrigen.setTitular(titularOrigen);

        cuentaDestino = new Cuenta();
        cuentaDestino.setNumeroCuenta(654321);
        cuentaDestino.setTipoMoneda(TipoMoneda.PESOS);
        cuentaDestino.setTitular(titularDestino);
    }

    @Test
    void transferir_exito() {
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino())).thenReturn(cuentaDestino);
        when(banelcoService.comprobarBanco(titularDestino.getDni())).thenReturn(true);

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("Transferencia realizada", respuesta.getMensaje());
        assertEquals("Exitoso", respuesta.getEstado());
        verify(cuentaService, times(1)).actualizarSaldoTransferencia(eq(cuentaOrigen), eq(cuentaDestino), eq(5000.0), eq(0.0));
        verify(movimientos, times(2)).save(any(Cuenta.class), any(), any(), any());
    }

    @Test
    void transferir_cuentaOrigenNoExiste() {
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(null);

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("Cuenta origen no encontrada", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
        verifyNoInteractions(cuentaService);
    }

    @Test
    void transferir_cuentaDestinoNoExiste() {
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino())).thenReturn(null);

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("Cuenta destino no encontrada", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
        verifyNoInteractions(cuentaService);
    }

    @Test
    void transferir_saldoInsuficiente() {
        transferenciaDto.setMonto(20000);  // Monto mayor al saldo en la cuenta origen

        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino())).thenReturn(cuentaDestino);

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("Saldo insuficiente", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
        verifyNoInteractions(cuentaService);
    }

    @Test
    void transferir_cuentasDiferenteTipoMoneda() {
        cuentaDestino.setTipoMoneda(TipoMoneda.DOLARES);

        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino())).thenReturn(cuentaDestino);

        transferenciaDto.setMoneda("PESOS");

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("Las cuentas son de diferente tipo", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
        verifyNoInteractions(cuentaService);
    }


    @Test
    void transferir_diferenteBancoYNoEsBanelco() {
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino())).thenReturn(cuentaDestino);

        when(banelcoService.comprobarBanco(titularDestino.getDni())).thenReturn(false);

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("El banco destino no es Banelco", respuesta.getMensaje());
        assertEquals("Fallido", respuesta.getEstado());
        verifyNoInteractions(cuentaService);
    }

    @Test
    void transferir_diferenteBancoEsBanelco() {
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findByNumeroCuenta(transferenciaDto.getCuentaDestino())).thenReturn(cuentaDestino);

        when(banelcoService.comprobarBanco(titularDestino.getDni())).thenReturn(true);

        RespuestaDto respuesta = transferenciaService.transferir(transferenciaDto);

        assertEquals("Transferencia realizada", respuesta.getMensaje());
        assertEquals("Exitoso", respuesta.getEstado());
    }
}
