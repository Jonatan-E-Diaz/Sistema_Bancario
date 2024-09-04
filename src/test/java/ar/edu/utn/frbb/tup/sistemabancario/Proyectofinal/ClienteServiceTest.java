package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.ClienteDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.NotPosibleException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.ClienteDao;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void darDeAltaCliente_clienteYaExiste() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678L);
        clienteDto.setNombre("Juan");
        clienteDto.setApellido("Pérez");
        clienteDto.setTipoPersona(String.valueOf(TipoPersona.PERSONA_FISICA));
        clienteDto.setBanco("Banco Nacional");
        clienteDto.setFechaNacimiento("2000-01-01");

        Cliente clienteExistente = new Cliente(clienteDto);

        Mockito.when(clienteDao.find(clienteDto.getDni())).thenReturn(clienteExistente);

        NotPosibleException exception = assertThrows(NotPosibleException.class, () -> {
            clienteService.darDeAltaCliente(clienteDto);
        });

        assertEquals("El cliente con DNI: 12345678 ya existe!!!", exception.getMessage());
    }

    @Test
    void darDeAltaCliente_clienteMenorDeEdad() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(87654321L);
        clienteDto.setNombre("Ana");
        clienteDto.setApellido("Gómez");
        clienteDto.setTipoPersona(String.valueOf(TipoPersona.PERSONA_FISICA));
        clienteDto.setBanco("Banco Internacional");
        clienteDto.setFechaNacimiento("2010-05-15");

        Mockito.when(clienteDao.find(clienteDto.getDni())).thenReturn(null);

        NotPosibleException exception = assertThrows(NotPosibleException.class, () -> {
            clienteService.darDeAltaCliente(clienteDto);
        });

        assertEquals("El cliente es menor de edad", exception.getMessage());
    }

    @Test
    void darDeAltaCliente_exito() throws NotPosibleException {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(11223344L);
        clienteDto.setNombre("Carlos");
        clienteDto.setApellido("Mendoza");
        clienteDto.setTipoPersona(String.valueOf(TipoPersona.PERSONA_FISICA));
        clienteDto.setBanco("Banco de la Nación");
        clienteDto.setFechaNacimiento("1985-03-10");

        Mockito.when(clienteDao.find(clienteDto.getDni())).thenReturn(null);
        Mockito.when(clienteDao.save(Mockito.any(Cliente.class))).thenReturn(new Cliente(clienteDto));

        Cliente cliente = clienteService.darDeAltaCliente(clienteDto);

        assertNotNull(cliente);
        assertEquals(clienteDto.getDni(), cliente.getDni());
    }

    @Test
    void buscarCliente_clienteNoExiste() {
        Mockito.when(clienteDao.find(12345678L)).thenReturn(null);

        ClienteDoesntExistException exception = assertThrows(ClienteDoesntExistException.class, () -> {
            clienteService.buscarCliente(12345678L);
        });

        assertEquals("No existe el cliente con DNI 12345678.", exception.getMessage());
    }

    @Test
    void agregarCuenta_clienteNoExiste() {
        Cuenta cuenta = new Cuenta();
        Mockito.when(clienteDao.find(12345678L)).thenReturn(null);

        ClienteDoesntExistException exception = assertThrows(ClienteDoesntExistException.class, () -> {
            clienteService.agregarCuenta(cuenta, 12345678L);
        });

        assertEquals("No existe el cliente con DNI 12345678.", exception.getMessage());
    }

    @Test
    void agregarCuenta_exito() throws ClienteDoesntExistException {
        Cliente cliente = new Cliente();
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setTipoMoneda(TipoMoneda.PESOS);

        Mockito.when(clienteDao.find(12345678L)).thenReturn(cliente);
        Mockito.when(clienteDao.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        clienteService.agregarCuenta(cuenta, 12345678L);

        Mockito.verify(clienteDao).save(cliente);
        assertTrue(cliente.getCuentas().contains(cuenta));
    }

    @Test
    void eliminarCliente_clienteNoExiste() {
        Mockito.when(clienteDao.find(12345678L)).thenReturn(null);

        ClienteDoesntExistException exception = assertThrows(ClienteDoesntExistException.class, () -> {
            clienteService.eliminarCliente(12345678L);
        });

        assertEquals("No existe el cliente con DNI 12345678.", exception.getMessage());
    }

    @Test
    void eliminarCliente_exito() throws ClienteDoesntExistException {
        Mockito.when(clienteDao.find(12345678L)).thenReturn(new Cliente());
        Mockito.when(clienteDao.delete(12345678L)).thenReturn(true);

        boolean result = clienteService.eliminarCliente(12345678L);

        assertTrue(result);
        Mockito.verify(clienteDao).delete(12345678L);
    }

    @Test
    void buscarCliente_exitoso() throws ClienteDoesntExistException {

        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");

        Mockito.when(clienteDao.find(12345678L)).thenReturn(cliente);

        Cliente resultado = clienteService.buscarCliente(12345678L);

        assertNotNull(resultado);
        assertEquals(12345678L, resultado.getDni());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
    }
}

