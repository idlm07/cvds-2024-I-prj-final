package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionSerrvice {
    private final CotizacionRepository cotizacionRepository;
    private final ProductoRepository productoRepository;
    private final VehiculoRepository vehiculoRepository;


    @Autowired
    public CotizacionSerrvice(CotizacionRepository cotizacionRepository, ProductoRepository productoRepository, VehiculoRepository vehiculoRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.productoRepository = productoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Cotizacion> listarCotizaciones(){
        return cotizacionRepository.findAll();
    }

    public Cotizacion encontrarCotizacion(Long id){
        return cotizacionRepository.findByIden(id).get(0);

    }

    public Cotizacion agregarAlCarritoPrimeraVez(Producto producto, Vehiculo vehiculo){
        Cotizacion cotizacion = null;
        if(vehiculo.productoApto(producto)){
            cotizacion = new Cotizacion(vehiculo);
            cotizacion.agregarProductoAlCarrito(producto);
            producto.agregarCotizacion(cotizacion);
            vehiculo.agregarCotizacion(cotizacion);
            cotizacionRepository.save(cotizacion);
            productoRepository.save(producto);
            vehiculoRepository.save(vehiculo);
        }
        return cotizacion;
    }

    public void agregarAlCarritoNVez(Producto producto, Cotizacion cotizacion){
        Vehiculo vehiculo = cotizacion.getVehiculo();
        if(vehiculo.productoApto(producto)){
            cotizacion.agregarProductoAlCarrito(producto);
            producto.agregarCotizacion(cotizacion);
            vehiculo.agregarCotizacion(cotizacion);
            cotizacionRepository.save(cotizacion);
            productoRepository.save(producto);
            vehiculoRepository.save(vehiculo);
        }

    }

    public void quitarDelCarrito(Producto producto, Cotizacion cotizacion){
        cotizacion.eliminarProductoDelCarrito(producto);
        producto.eliminarCotizacion(cotizacion);
        if (cotizacion.getProductosCotizacion().isEmpty()) {
            cotizacion.setEstado(Cotizacion.ELIMINADO);
        }
        cotizacionRepository.save(cotizacion);
        productoRepository.save(producto);
    }

    public float calcularTotalCarrito(Cotizacion cotizacion){
        float total = 0;
        for (Producto producto : cotizacion.getProductosCotizacion()) {
        total += producto.getValor(); 
        }
    return total;
    }

    public List<Producto> verCarrito(Long cotizacionId){
        Cotizacion cotizacion = cotizacionRepository.findByIden(cotizacionId).get(0);
        return cotizacion.getProductosCotizacion();

    }
}
