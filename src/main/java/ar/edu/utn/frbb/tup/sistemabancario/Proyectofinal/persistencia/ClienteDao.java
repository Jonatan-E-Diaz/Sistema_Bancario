package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia;

import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.persistencia.entity.ClienteEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteDao extends AbstractBaseDao {

public Cliente save(Cliente cliente){
    ClienteEntity entity= new ClienteEntity(cliente);
    getInMemoryDatabase().put(entity.getId(),entity);
    return cliente;
}

public Cliente find(long dni){
    for (Object object : getInMemoryDatabase().values())
    {
        ClienteEntity clienteEntity = (ClienteEntity) object;
        if (clienteEntity.getDni()==dni)
            return clienteEntity.toCliente();
    }
    return null;
}

public boolean delete(long dni) {
    return getInMemoryDatabase().remove(dni) != null;
}

@Override
public String getEntityName(){
    return "CLIENTE";
}

}
