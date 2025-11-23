package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.SolicitudServicio;

import java.util.List;

public interface SolicitudServicioService {

    SolicitudServicio add(Long servicioId, Long cultivoId, SolicitudServicio solicitudServicio);
    List<SolicitudServicio> findAll();
    SolicitudServicio edit(Long id, SolicitudServicio solicitudServicio);
    void deleteById(Long id);
}
