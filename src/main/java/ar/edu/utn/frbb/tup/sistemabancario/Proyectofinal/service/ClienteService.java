package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.ClienteDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.NotPosibleException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.ClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    public Cliente darDeAltaCliente(ClienteDto clienteDto) throws NotPosibleException {

        Cliente cliente = new Cliente(clienteDto);
        if (clienteDao.find(cliente.getDni())!=null){
            throw new NotPosibleException("El cliente con DNI: "+cliente.getDni()+" ya existe!!!");//AGREGAR EXCEPTION
        } else if (cliente.getEdad()<18) {
            throw new NotPosibleException("El cliente es menor de edad");
        }
       return clienteDao.save(cliente);
    }

    public Cliente buscarCliente(long dni) {
        Cliente cliente = clienteDao.find(dni);

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

    return cliente;
    }

    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws ClienteDoesntExistException {
        Cliente titular = buscarCliente(dniTitular);
        cuenta.setTitular(titular);

        titular.addCuenta(cuenta);

        clienteDao.save(titular);
    }

    public boolean eliminarCliente(Long dni) throws ClienteDoesntExistException {
        Cliente clienteEliminar = clienteDao.find(dni);
        if (clienteEliminar == null) {
            throw new ClienteDoesntExistException("No existe el cliente con DNI " + dni + ".");
        }else {
            return clienteDao.delete(dni);
        }
    }



}
