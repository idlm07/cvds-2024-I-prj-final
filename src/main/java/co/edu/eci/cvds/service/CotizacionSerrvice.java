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
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import org.javamoney.moneta.Money;

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
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        if(vehiculo.productoApto(producto)){
            cotizacion.setEstado(Cotizacion.EN_PROCESO);
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
            if(cotizacion.getEstado().equals(Cotizacion.ELIMINADO)) cotizacion.setEstado(Cotizacion.EN_PROCESO);
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
    
        public Money calcularTotalCarritoEnPesos(Cotizacion cotizacion) {
            CurrencyUnit cop = Monetary.getCurrency("COP");
            Money totalEnPesos = Money.zero(cop);
    
            for (Producto producto : cotizacion.getProductosCotizacion()) {
                CurrencyUnit monedaProducto = Monetary.getCurrency(producto.getMoneda());
                Money precioProducto = Money.of(producto.getValor(), monedaProducto);
    
                // Convertir precio del producto a pesos colombianos
                ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider();
                CurrencyConversion conversion = rateProvider.getCurrencyConversion(monedaProducto);
                Money precioProductoEnPesos = precioProducto.with(conversion);
    
                totalEnPesos = totalEnPesos.add(precioProductoEnPesos);
            }
    
            return totalEnPesos;
    }
}

