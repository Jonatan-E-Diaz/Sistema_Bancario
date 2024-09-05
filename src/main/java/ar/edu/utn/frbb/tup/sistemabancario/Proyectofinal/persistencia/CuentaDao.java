package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.entity.CuentaEntity;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class CuentaDao extends AbstractBaseDao{

    public Cuenta find(long dni) {
        if (getInMemoryDatabase().get(dni) == null) {
            return null;
        }
        return ((CuentaEntity) getInMemoryDatabase().get(dni)).toCuenta();
    }

    public void save(Cuenta cuenta) {
        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);
        getInMemoryDatabase().put(cuenta.getNumeroCuenta(),cuentaEntity);
    }

    public Set<Cuenta> getAllCuentas(long dni) {
        Set<Cuenta> cuentas = new HashSet<>();
        for (Object object : getInMemoryDatabase().values()) {
            CuentaEntity cuentaEntity = (CuentaEntity) object;
            if (cuentaEntity.getTitular().getDni()==dni) {
                cuentas.add(cuentaEntity.toCuenta());
            }
        }
        return cuentas;
    }
    public Cuenta findByNumeroCuenta(long numeroCuenta) {
        for (Object object : getInMemoryDatabase().values()) {
            CuentaEntity cuentaEntity = (CuentaEntity) object;
            if (cuentaEntity.getNumeroCuenta() == numeroCuenta) {
                return cuentaEntity.toCuenta();
            }
        }
        return null;
    }

    public void update(Cuenta cuenta) {
        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);
        getInMemoryDatabase().put(cuenta.getNumeroCuenta(), cuentaEntity);
    }

    @Override
    protected String getEntityName() {
        return "CUENTA";
    }
}
