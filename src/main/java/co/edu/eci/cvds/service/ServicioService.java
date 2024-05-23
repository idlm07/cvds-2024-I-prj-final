package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Servicio;
import co.edu.eci.cvds.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio getServicioById(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public void deleteServicio(Integer id) {
        servicioRepository.deleteById(id);
    }
}
