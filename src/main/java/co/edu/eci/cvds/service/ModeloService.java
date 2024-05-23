package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Modelo;
import co.edu.eci.cvds.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    public Modelo saveModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public Modelo getModeloById(Integer id) {
        return modeloRepository.findById(id).orElse(null);
    }

    public List<Modelo> getAllModelos() {
        return modeloRepository.findAll();
    }

    public void deleteModelo(Integer id) {
        modeloRepository.deleteById(id);
    }
}
