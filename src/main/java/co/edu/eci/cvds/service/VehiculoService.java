package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final ProductoRepository productoRepository;


    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository,ProductoRepository productoRepository, EntityManager em) {
        this.vehiculoRepository = vehiculoRepository;
        this.productoRepository = productoRepository;
    }

    public Vehiculo agregarVehiculo(Vehiculo vehiculo) {

        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo getVehiculo(String marca, String modelo) {
        return vehiculoRepository.findByMarcaAndModel(String.valueOf(marca), String.valueOf(modelo)).get(0);
    }



    public void agregarProducto(String marca, String modelo,Producto producto){
        Vehiculo currentVehicle = this.getVehiculo(marca, modelo);
        currentVehicle.anadirProducto(producto);
        producto.agregarVehiculo(currentVehicle);
        vehiculoRepository.save(currentVehicle);
        productoRepository.save(producto);
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Set<String> getMarcas(){
        HashSet<String> marcas = new HashSet<>();
        for(Vehiculo vehiculo : this.getVehiculos()){
            marcas.add(vehiculo.getMarca());
        }
        return marcas;
    }

    public List<Vehiculo> getModelosMarca(String marca) {

        return vehiculoRepository.findByMarca(marca);
    }

    public int[] getMinMaxYear(String marca, String modelo) {
        int creado = Integer.parseInt(vehiculoRepository.findByMarcaAndModel(marca,modelo).get(0).getYearVehicle());
        int actual = LocalDate.now().getYear();
        return new int[]{creado,actual};
    }
}
