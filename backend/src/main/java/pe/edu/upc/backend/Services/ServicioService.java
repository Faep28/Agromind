package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.Servicio;

import java.util.List;

public interface ServicioService {

    Servicio add(Servicio servicio);
    List<Servicio> findAll();
    Servicio edit(Long id, Servicio servicio);
    void deleteById(Long id);
}
