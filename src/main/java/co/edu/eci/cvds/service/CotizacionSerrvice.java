package co.edu.eci.cvds.service;

import co.edu.eci.cvds.Exception.LincolnLinesException;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import java.util.List;

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
    /**
    * Función que convierte cualquier moneda a pesos colombianos
    * @param origen, moneda que convierte cualquier divisa a USD para luego
     convertirlo a COP
    * @return, moneda convertida a COP
    *
    */

    private Money convertidorCop(Money origen){
        ExchangeRateProvider proveedor = MonetaryConversions.getExchangeRateProvider("ECB", "IMF");
        CurrencyConversion conversion = proveedor.getCurrencyConversion("USD");
        Money productMoneyUsd = origen.with(conversion);
        return Money.of(productMoneyUsd.getNumber().floatValue()*3900,"COP");
    }

    /**
     * Función que devuelve lista de cotizaciones en la Base de Datos
     *
     * @return, lista de vehiculos que esta en la base de datos.
     */

    public List<Cotizacion> listarCotizaciones(){
        return cotizacionRepository.findAll();
    }

    /**
     *
     * @return lista de las cotizaciones que ya fueron agendadas.
     */
    public List<Cotizacion> cotizacionesAgendadas(){
        return cotizacionRepository.findByCita();
    }

    /**
     * Función que devuelve la información de una cotización que se desea encontrar
     * @param id, identificador de la cotización que se desea encontrar
     * @return Cotizacion con el id especificado.
     */
    public Cotizacion encontrarCotizacion(Long id){
        return cotizacionRepository.findByIden(id).get(0);

    }

    /**
     * Funcion que agrega un producto a la lista de productos de cotizaciones
     * @param producto, producto a agregar al carrito.
     * @param vehiculo, vehiculo para el cual se hace la consulta
     * @param cotizacion, cotizacion en cuestion
     * @return cotizacion especificada.
     * @throws LincolnLinesException DATOS_FALTANTES si tanto vehiculo como cotizacion son null
     * VEHICULO_NO_COMPATIBLE si el vehiculo ingresado es diferente al de la cotizacion
     */
    public Cotizacion agregarAlCarrito(Producto producto, Vehiculo vehiculo, Cotizacion cotizacion) throws LincolnLinesException {
        if(cotizacion == null && vehiculo == null) throw new LincolnLinesException(LincolnLinesException.DATOS_FALTANTES);
        if(cotizacion != null && !cotizacion.getVehiculo().equals(vehiculo)) throw new LincolnLinesException(LincolnLinesException.VEHICULO_NO_COMPATIBLE);
        if(cotizacion == null) cotizacion = new Cotizacion(vehiculo);
        cotizacion.setEstado(Cotizacion.EN_PROCESO);
        cotizacion.agregarProductoAlCarrito(producto);
        producto.agregarCotizacion(cotizacion);
        vehiculo.agregarCotizacion(cotizacion);
        cotizacionRepository.save(cotizacion);
        productoRepository.save(producto);
        vehiculoRepository.save(vehiculo);
        return cotizacion;
    }


    /**
     * Metodo que quita producto del carrito
     * @param producto, producto a quitar
     * @param cotizacion cotizacion de la cual se va a quitar el producto
     */
    public void quitarDelCarrito(Producto producto, Cotizacion cotizacion){
        cotizacion.eliminarProductoDelCarrito(producto);
        producto.eliminarCotizacion(cotizacion);
        if (cotizacion.getProductosCotizacion().isEmpty()) {
            cotizacion.setEstado(Cotizacion.ELIMINADO);
        }
        cotizacionRepository.save(cotizacion);
        productoRepository.save(producto);
    }


    /**
     * Funcion que investiga los productos que estan incluidos en el carrito de una cotizacion en especifico
     * @param cotizacionId, identificador del cotizador  a avegiruar
     * @return lita de productos
     */
    public List<Producto> verCarrito(Long cotizacionId){
        Cotizacion cotizacion = cotizacionRepository.findByIden(cotizacionId).get(0);
        return cotizacion.getProductosCotizacion();

    }

    /**
     * Funcion que convierte cualquier producto del carrito a pesos colombianos
     * @param producto, producto  a convertir
     * @return total de los productos en el carrito.
     */
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

    /**
     * Calcula el total teniendo en cuenta, total del carrito, impuesto y descuento
     * @param cotizacion
     * @return total a paraagar.
     */

    public float cotizacionTotal(Cotizacion cotizacion){
        Money total = calcularTotalCarrito(cotizacion);
        float totalDescuento ;
        float totalImpuesto ;
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

    /**
     * Metodo para separar cita
     * @param cita, fecha y hora en la que se va a realizar la cita
     * @param ciudad, ciudad donde dse va a recoger el vehiculo
     * @param direccion, direccion donde se va a recoger el vehiculo
     * @param cotizacion
     * @throws LincolnLinesException FECHA_NO_DISPONIBLE si alguna de las cotizaciones ya agendadas se  ruza con la actual.
     */
    public void agendarCita(LocalDateTime cita, String ciudad,String direccion,Cotizacion cotizacion) throws LincolnLinesException {
        if(!fechaDisponible(cita)) throw new LincolnLinesException(LincolnLinesException.FECHA_NO_DISPONIBLE);
        cotizacion.agendar(cita,ciudad,direccion);
        cotizacionRepository.save(cotizacion);

    }

    /**
     * Funcion que se encarta de verificar que una clase tenga baja punteria
     * @param fechaEsperada, fecha la cul se va a comparar con lods demas
     * @return boolean que inidca si se va a poder agendar cita
     */

    private boolean fechaDisponible(LocalDateTime fechaEsperada){
        LocalDate fechaBase;
        Duration entreCitas;
        Duration nowVCita = Duration.between(LocalDateTime.now(),fechaEsperada);
        LocalTime horaInicio = LocalTime.of(8,0);
        LocalTime horaFin = LocalTime.of(15,0);
        if(fechaEsperada.isEqual(LocalDateTime.now()) || fechaEsperada.isBefore(LocalDateTime.now())
                || fechaEsperada.toLocalTime().isBefore(horaInicio) || fechaEsperada.toLocalTime().isAfter(horaFin)
                || nowVCita.getSeconds() <= 7200) return false;
        for(Cotizacion c: this.cotizacionesAgendadas()){
            fechaBase = c.getCita().toLocalDate();
            entreCitas = Duration.between(c.getCita(),fechaEsperada);
            if(fechaBase.isEqual(fechaEsperada.toLocalDate()) && entreCitas.getSeconds() <= 7200) return false;
        }
        return true;
    }


}