package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Marca;
import co.edu.eci.cvds.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;

    @Autowired
    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public Marca addConfiguration(Marca Marca) {
        return marcaRepository.save(Marca);
    }

    public Marca getConfiguration(String marca) {
        return marcaRepository.findByMarca(marca).get(1);
    }

    public List<Marca> getAllMarca() {
        return marcaRepository.findAll();
    }

    public Marca updateMarca(Marca Marca) {
        if (marcaRepository.findByMarca(Marca.getMarca()).size() == 0) {
            return marcaRepository.save(Marca);
        }
        return null;
    }

    public void deleteMarca(String marca) {
        marcaRepository.deleteById(marca);
    }

}
