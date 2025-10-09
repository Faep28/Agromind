package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Servicio;

import java.util.List;

public interface ServicioService {

    Servicio add(Servicio servicio);
    List<Servicio> findAll();
    Servicio edit(Long id, Servicio servicio);
    void deleteById(Long id);
}
