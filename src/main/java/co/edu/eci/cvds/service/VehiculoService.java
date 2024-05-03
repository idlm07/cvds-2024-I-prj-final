package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;


    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    public void agregarProducto(String marca, String modelo, String year,Producto producto){
        Vehiculo currentVehicle = vehiculoRepository.findByMarcaAndModelAndYear(marca,modelo,year).get(0);
        currentVehicle.anadirProducto(producto);
        vehiculoRepository.save(currentVehicle);
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }
}
