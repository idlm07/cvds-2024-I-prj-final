package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Auto;
import co.edu.eci.cvds.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {
    private final AutoRepository autoRepository;

    @Autowired
    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public Auto addConfiguration(Auto auto) {
        return autoRepository.save(auto);
    }

    public Auto getConfiguration(int id) {
        return autoRepository.findByid(id).get(1);
    }

    public List<Auto> getAllAuto() {
        return autoRepository.findAll();
    }

    public Auto updateAuto(Auto auto) {
        if (autoRepository.findByid(auto.getId()).size() == 0) {
            return autoRepository.save(auto);
        }
        return null;
    }

    public void deleteAuto(int id) {
        autoRepository.deleteById(id);
    }

}