package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoCuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.TipoMoneda;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.ClienteDoesntExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.CuentaAlreadyExistException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.exceptions.NotPosibleException;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.CuentaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CuentaDao cuentaDao;

    public Cuenta crearCuenta(CuentaDto cuentaDto) throws NotPosibleException, CuentaAlreadyExistException, ClienteDoesntExistException {
        Cuenta cuenta = agregarTitular(cuentaDto);

        if (cuentaDao.find(cuenta.getTitular().getDni()) != null){
            throw new NotPosibleException("El cliente ya tiene una cuenta");
        }
        if (cuenta.getTitular().tieneCuenta(cuenta.getTipoCuenta(),cuenta.getTipoMoneda())){
            throw new CuentaAlreadyExistException("El cliente ya tiene una cuenta de este tipo");
        }
        if (cuenta.getTipoCuenta()== TipoCuenta.CUENTA_CORRIENTE && cuenta.getTipoMoneda()==TipoMoneda.DOLARES){
            throw new NotPosibleException("El cliente no puede tener una cuenta corriente en dolares");
        }

        clienteService.agregarCuenta(cuenta, cuentaDto.getDniCliente());

        cuentaDao.save(cuenta);

        return cuenta;
    }
    private Cuenta agregarTitular(CuentaDto cuentaDto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.valueOf(cuentaDto.getTipoCuenta()));
        cuenta.setTipoMoneda(TipoMoneda.valueOf(cuentaDto.getTipoMoneda()));

        Cliente cliente = clienteService.buscarCliente(cuentaDto.getDniCliente());
        cuenta.setTitular(cliente);

        return cuenta;
    }

    public Set<Cuenta> buscarCuentas(long dni) {
        Set<Cuenta> cuentasConTitular = cuentaDao.getAllCuentas(dni);
        Set<Cuenta> cuentas = cuentasConTitular.stream()
                .map(cuenta -> {
                    Cuenta copia = new Cuenta();
                    copia.setNumeroCuenta(cuenta.getNumeroCuenta());
                    copia.setTipoCuenta(cuenta.getTipoCuenta());
                    copia.setTipoMoneda(cuenta.getTipoMoneda());
                    copia.setSaldo(cuenta.getSaldo());
                    return copia;
                })
                .collect(Collectors.toSet());
        return cuentas;
    }

    public void actualizarSaldo(Cuenta cuenta, double monto){
        cuenta.setSaldo(cuenta.getSaldo()+ monto);
        cuentaDao.update(cuenta);
    }
}
