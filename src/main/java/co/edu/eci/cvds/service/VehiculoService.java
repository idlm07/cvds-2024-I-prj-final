package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final ProductoRepository productoRepository;


    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository,ProductoRepository productoRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.productoRepository = productoRepository;
    }

    public Vehiculo agregarVehiculo(Vehiculo vehiculo) {

        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo getVehiculo(String marca, String modelo, String year) {
        return vehiculoRepository.findByMarcaAndModelAndYearVehicle(String.valueOf(marca), String.valueOf(modelo), String.valueOf(year)).get(0);
    }

    public void agregarProducto(String marca, String modelo, String year,Producto producto){
        Vehiculo currentVehicle = vehiculoRepository.findByMarcaAndModelAndYearVehicle(marca,modelo,year).get(0);
        currentVehicle.anadirProducto(producto);
        producto.agregarVehiculo(currentVehicle);
        vehiculoRepository.save(currentVehicle);
        productoRepository.save(producto);
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }
}
