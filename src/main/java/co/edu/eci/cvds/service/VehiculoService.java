package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo saveVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo getVehiculoById(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    public Vehiculo getVehiculoByMarca(String marca) {
        return vehiculoRepository.findByMarca(marca);
    }

    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }

    public void deleteVehiculo(Integer id) {
        vehiculoRepository.deleteById(id);
    }
}
