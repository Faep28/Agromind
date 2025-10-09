package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entitie.Cliente;
import pe.edu.upc.backend.Entitie.Parcela;
import pe.edu.upc.backend.Entitie.User;
import pe.edu.upc.backend.Repository.ClienteRepository;
import pe.edu.upc.backend.Repository.ParcelaRepository;
import pe.edu.upc.backend.Service.ParcelaService;

import java.util.List;

@Service
public class ParcelaServiceImpl implements ParcelaService {

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public Parcela add(Parcela parcela) {
        // Simplemente guardamos la parcela, ya que el cliente ya ha sido asociado en el controlador
        return parcelaRepository.save(parcela);
    }


    @Override
    public List<Parcela> findAll() {
        return parcelaRepository.findAll();  // Obtener todas las parcelas
    }
    @Override
    public Parcela edit(Parcela parcela) {
        // Verifica si la parcela existe antes de actualizar
        if (parcelaRepository.existsById(parcela.getId())) {
            return parcelaRepository.save(parcela);  // Si existe, la actualiza
        }
        return null;  // Si no existe, retorna null
    }
    @Override
    public void deleteById(Long id) {
        if (parcelaRepository.existsById(id)) {
            parcelaRepository.deleteById(id);  // Eliminar la parcela
        }
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

}
