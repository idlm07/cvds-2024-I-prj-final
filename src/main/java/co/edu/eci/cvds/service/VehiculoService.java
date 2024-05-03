package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final ProductoService productoService;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, ProductoService productoService) {
        this.vehiculoRepository = vehiculoRepository;
        this.productoService = productoService;
    }

    public Vehiculo agregarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public void agregarProducto(String marca, String modelo, String year,Producto producto){
        Vehiculo currentVehicle = vehiculoRepository.findByMarcaAndModelAndYear(marca,modelo,year).get(0);
        currentVehicle.anadirProducto(producto);
        producto.agregarVehiculo(currentVehicle);
        vehiculoRepository.save(currentVehicle);
        productoService.agregarProducto(producto);

    }
}
