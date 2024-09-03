package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoCuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMoneda;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.ClienteDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaAlreadyExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.NotPosibleException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.ClienteService;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CuentaServiceTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaDao cuentaDao;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCuenta_clienteNoExiste() throws ClienteDoesntExistException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniCliente(12345678L);
        cuentaDto.setTipoCuenta("CUENTA_CORRIENTE");
        cuentaDto.setTipoMoneda("PESOS");

        Mockito.when(clienteService.buscarCliente(12345678L)).thenThrow(new ClienteDoesntExistException("No existe el cliente con DNI " + cuentaDto.getDniCliente() + "."));

        ClienteDoesntExistException exception = assertThrows(ClienteDoesntExistException.class, () -> {
            cuentaService.crearCuenta(cuentaDto);
        });

        assertEquals("No existe el cliente con DNI 12345678.", exception.getMessage());
    }

    @Test
    void crearCuenta_cuentaYaExiste() throws ClienteDoesntExistException, NotPosibleException, CuentaAlreadyExistException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniCliente(12345678L);
        cuentaDto.setTipoCuenta("CAJA_AHORRO");
        cuentaDto.setTipoMoneda("PESOS");

        // Simulamos el cliente
        Cliente cliente = Mockito.mock(Cliente.class);
        Cuenta cuentaExistente = Mockito.mock(Cuenta.class); // Simulamos una cuenta existente

        // Mockeamos las interacciones
        Mockito.when(clienteService.buscarCliente(12345678L)).thenReturn(cliente);
        Mockito.when(cliente.tieneCuenta(TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS)).thenReturn(true); // El cliente ya tiene la cuenta
        Mockito.when(cuentaDao.find(12345678L)).thenReturn(cuentaExistente); // Simulamos que ya existe la cuenta en la base de datos

        // Verificamos que se lanza la excepción correcta
        assertThrows(CuentaAlreadyExistException.class, () -> {
            cuentaService.crearCuenta(cuentaDto);
        });
    }


    @Test
    void crearCuenta_cuentaCorrienteDolaresNoPermitida() throws ClienteDoesntExistException, NotPosibleException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniCliente(12345678L);
        cuentaDto.setTipoCuenta("CUENTA_CORRIENTE");
        cuentaDto.setTipoMoneda("DOLARES");

        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);
        Mockito.when(clienteService.buscarCliente(12345678L)).thenReturn(cliente);

        NotPosibleException exception = assertThrows(NotPosibleException.class, () -> {
            cuentaService.crearCuenta(cuentaDto);
        });

        assertEquals("El cliente no puede tener una cuenta corriente en dolares", exception.getMessage());
    }

    @Test
    void crearCuenta_exito() throws ClienteDoesntExistException, NotPosibleException, CuentaAlreadyExistException {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniCliente(12345678L);
        cuentaDto.setTipoCuenta("CAJA_AHORRO");
        cuentaDto.setTipoMoneda("PESOS");

        // Simulamos el cliente
        Cliente cliente = Mockito.mock(Cliente.class);

        Mockito.when(clienteService.buscarCliente(12345678L)).thenReturn(cliente);
        Mockito.when(cuentaDao.find(12345678L)).thenReturn(null);
        Mockito.when(cliente.tieneCuenta(TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS)).thenReturn(false);

        Cuenta cuenta = cuentaService.crearCuenta(cuentaDto);

        assertNotNull(cuenta);
        assertEquals(cliente, cuenta.getTitular());
        assertEquals(TipoCuenta.CAJA_AHORRO, cuenta.getTipoCuenta());
        assertEquals(TipoMoneda.PESOS, cuenta.getTipoMoneda());
    }

}
