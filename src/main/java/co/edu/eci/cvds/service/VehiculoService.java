package co.edu.eci.cvds.service;

import co.edu.eci.cvds.SpringApplicationCvds;
import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase Service de Vehiculo
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final ProductoRepository productoRepository;



    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository,ProductoRepository productoRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Metodo que guarda un vehiculo en la base de datos
     * @param vehiculo, vehiculo a registrar
     * @return vehiculo registrado
     */
    public Vehiculo agregarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    /**
     * Funcion que busca un vehiculo en especifico
     * @param marca del vehiculo
     * @param modelo del vehiculo
     * @return vehiculo acorde a la marca y  modelo indicados
     */

    public Vehiculo getVehiculo(String marca, String modelo) {
        marca = SpringApplicationCvds.stringStandar(marca);
        modelo = SpringApplicationCvds.stringStandar(modelo);
        return vehiculoRepository.findByMarcaAndModel(marca, modelo).get(0);
    }

    /**
     * Metodo que asocia un vehiculo con un producto es decir, indica que productos son aptos para que vehiculo.
     * @param marca del carro
     * @param modelo del carro
     * @param producto, nombre del producto apto para el vehiculo
     */

    public Vehiculo agregarProducto(String marca, String modelo,String producto) throws LincolnLinesException {
        Vehiculo currentVehicle = this.getVehiculo(marca, modelo);
        Producto productoEncontrado = productoRepository.findByNombre(SpringApplicationCvds.stringStandar(producto)).get(0);
        currentVehicle.anadirProducto(productoEncontrado);
        productoRepository.save(productoEncontrado);
        return vehiculoRepository.save(currentVehicle);

    }

    /**
     *
     * @return lista de vehiculos registrados en la base de datos
     */
    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }

    /**
     * Indica las marcas registradas en la base de datos
     * @return nombres de las marcas registradas en la base de datos.
     */
    public Set<String> getMarcas(){
        HashSet<String> marcas = new HashSet<>();
        for(Vehiculo vehiculo : this.getVehiculos()){
            marcas.add(vehiculo.getMarca());
        }
        return marcas;
    }

    /**
     * Funcion que indica los modelos disponibles para una marca en especifico
     * @param marca marca que se desea averiguar
     * @return lista de los modelos disponibles para dicha marca.
     */
    public List<Vehiculo> getModelosMarca(String marca) {

        return vehiculoRepository.findByMarca(marca);
    }

    /**
     * Funcion que indica el año desde que se creo el modelo del vehiculo hasta la fecha actual
     * @param marca del vehiculo
     * @param modelo del vehiculo
     * @return lista de interos de dos elementos, año inicial y año final.
     */

    public int[] getMinMaxYear(String marca, String modelo) {
        int creado = Integer.parseInt(vehiculoRepository.findByMarcaAndModel(marca,modelo).get(0).getYearVehicle());
        int actual = LocalDate.now().getYear();
        return new int[]{creado,actual};
    }

    public void limpiarTabla(){
        vehiculoRepository.deleteAllInBatch();
    }

    /**
     * Metodo que inidca los productos que se pueden utilizar para un vehiculo
     * @param marca, marca del vehiculo
     * @param modelo, modelo del vehiculo
     * @return conjunto de productos aptos para el vehiculo
     */
    public Set<Producto> listraProductosOptimos (String marca, String modelo){
        Vehiculo vehiculo = this.getVehiculo(marca,modelo);
        return vehiculo.getProductosVehiculo();
    }

    public Set<Cotizacion> listarCotizaciones(String marca, String modelo){
        Vehiculo encontrado = this.getVehiculo(marca,modelo);
        return encontrado.getCotizaciones();
    }
}