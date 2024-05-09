package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private List<LocalDateTime> citas = new ArrayList<>();

    @Autowired
    public CotizacionSerrvice(CotizacionRepository cotizacionRepository, ProductoRepository productoRepository, VehiculoRepository vehiculoRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.productoRepository = productoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    private Money convertidorCop(Money origen){
        ExchangeRateProvider proveedor = MonetaryConversions.getExchangeRateProvider("ECB", "IMF");
        CurrencyConversion conversion = proveedor.getCurrencyConversion("USD");
        Money productMoneyUsd = origen.with(conversion);
        return Money.of(productMoneyUsd.getNumber().floatValue()*3900,"COP");
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



    public List<Producto> verCarrito(Long cotizacionId){
        Cotizacion cotizacion = cotizacionRepository.findByIden(cotizacionId).get(0);
        return cotizacion.getProductosCotizacion();

    }

    private Money moneyConversion(Producto producto){
        if(!producto.getMoneda().equals("COP")) return convertidorCop(Money.of(producto.getValor(),producto.getMoneda()));
        else return Money.of(producto.getValor(),producto.getMoneda());
    }

    public Money calcularTotalCarrito(Cotizacion cotizacion){
        Money total = Money.zero(Monetary.getCurrency("COP"));
        for (Producto producto : verCarrito(cotizacion.getIden())) {
           total = total.add(moneyConversion(producto));
        }
        return total;
    }

    public float cotizacionTotal(Cotizacion cotizacion){
        Money total = calcularTotalCarrito(cotizacion);
        float totalDescuento = 0;
        float totalImpuesto = 0;
        Money mTotalDescuento;
        Money mTotalImpuesto;
        for(Producto producto : verCarrito(cotizacion.getIden())){
            totalDescuento = producto.getValor() * producto.getDescuento();
            totalImpuesto = (producto.getValor() - totalDescuento) * producto.getImpuesto();
            if(!producto.getMoneda().equals("COP")){
               mTotalDescuento = convertidorCop(Money.of(totalDescuento,producto.getMoneda()));
               mTotalImpuesto = convertidorCop(Money.of(totalImpuesto,producto.getMoneda()));
           }else{
                mTotalDescuento = Money.of(totalDescuento,producto.getMoneda());
                mTotalImpuesto = Money.of(totalImpuesto,producto.getMoneda());
            }
            total = total.subtract(mTotalDescuento);
            total = total.add(mTotalImpuesto);
        }
       return total.getNumber().floatValue();
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
    public boolean verificarDisponibilidadCita(String fechaHoraStr) {
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        // Verificar si la fecha y hora de la cita se superponen con las citas existentes
        for (LocalDateTime citaExistente : citas) {
            if (fechaHora.isEqual(citaExistente) || fechaHora.isAfter(citaExistente) && fechaHora.isBefore(citaExistente.plusHours(1))) {
                return false;
            }
        }
        return true; // No hay superposición, la cita está disponible
    }

    public void registrarCita(String fechaHoraStr) {
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        citas.add(fechaHora); // Agregar la nueva cita a la lista de citas
    }
    public Cotizacion solicitarCitaDesdeCarrito(Producto producto, Vehiculo vehiculo, String fechaHora, String servicio, String cliente) {
        if (verificarDisponibilidadCita(fechaHora)) {
            registrarCita(fechaHora);
            return agregarAlCarritoPrimeraVez(producto, vehiculo);
        } else {
            return null; // No se puede solicitar la cita porque no hay disponibilidad
        }
     
   
 }
}