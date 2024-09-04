package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.HistorialDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMovimiento;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.HistorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistorialServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @InjectMocks
    private HistorialService historialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarHistorial_cuentaExiste_devuelveHistorial() throws CuentaDoesntExistException {

        long cuentaId = 12345L;
        Cuenta cuentaMock = new Cuenta();
        cuentaMock.setNumeroCuenta(cuentaId);
        Set<Movimientos> movimientos = new HashSet<>();
        movimientos.add(new Movimientos(100, "Deposito", 123456, LocalDate.now(), TipoMovimiento.DEPOSITO));
        movimientos.add(new Movimientos(50, "Retiro", 123457, LocalDate.now(), TipoMovimiento.RETIRO));
        cuentaMock.setMovimientos(movimientos);

        when(cuentaDao.findByNumeroCuenta(cuentaId)).thenReturn(cuentaMock);

        HistorialDto historial = historialService.consultarHistorial(cuentaId);

        assertNotNull(historial);
        assertEquals(cuentaId, historial.getNumeroCuenta());
        assertEquals(2, historial.getHistorial().size());
        verify(cuentaDao, times(1)).findByNumeroCuenta(cuentaId);
    }

    @Test
    void consultarHistorial_cuentaNoExiste_lanzaExcepcion() {

        long cuentaId = 12345L;

        when(cuentaDao.findByNumeroCuenta(cuentaId)).thenReturn(null);

        assertThrows(CuentaDoesntExistException.class, () -> {
            historialService.consultarHistorial(cuentaId);
        });

        verify(cuentaDao, times(1)).findByNumeroCuenta(cuentaId);
    }
}
