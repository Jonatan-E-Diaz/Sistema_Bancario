package ar.edu.utn.frbb.tup.sistemabancario.Proyectofinal.service;

import org.springframework.stereotype.Service;

@Service
public class BanelcoService {

    public boolean comprobarBanco(long dni){
        return (dni% 2 == 0);
    }


}
