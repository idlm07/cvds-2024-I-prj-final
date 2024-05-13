package co.edu.eci.cvds.service;

import co.edu.eci.cvds.SpringApplicationCvds;
import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.ClienteRepository;
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

/**
 * Clase Service de cotizacion
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */
@Service
public class CotizacionService {
    private final CotizacionRepository cotizacionRepository;
    private final ProductoRepository productoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public CotizacionService(CotizacionRepository cotizacionRepository, ProductoRepository productoRepository, VehiculoRepository vehiculoRepository,ClienteRepository clienteRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.productoRepository = productoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
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
     * @return, lista de cotizaciones que esta en la base de datos.
     */

    public List<Cotizacion> listarCotizaciones(){
        return cotizacionRepository.findAll();
    }

    /**
     *Funcion que indica cotizaciones con citas agendadas
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
    public Cotizacion encontrarCotizacion(long id){
        return cotizacionRepository.findByIden(id).get(0);

    }

    /**
     * Funcion que agrega un producto a la lista de productos de cotizaciones
     * @param producto, nombre del producto a agregar al carrito.
     * @param marca, marca del vehiculo para el cual se hace la consulta
     * @param modelo, modelo del vehiculo para el cual se hace la consulta
     * @param cotizacion, Id de la cotizacion en cuestion
     * @return cotizacion especificada.
     * @throws LincolnLinesException DATOS_FALTANTES si tanto vehiculo como cotizacion son null
     * VEHICULO_NO_COMPATIBLE si el vehiculo ingresado es diferente al de la cotizacion
     */
    public Cotizacion agregarAlCarrito(String producto, String marca,String modelo, long cotizacion) throws LincolnLinesException {
        Vehiculo vehiculo;
        Cotizacion cotizacionEncontrada;
        Producto productoEncontrado;
        if(cotizacion == -1 && (marca == null || modelo == null)) throw new LincolnLinesException(LincolnLinesException.DATOS_FALTANTES);
        else if(cotizacion == -1){
            vehiculo = vehiculoRepository.findByMarcaAndModel(SpringApplicationCvds.stringStandar(marca), SpringApplicationCvds.stringStandar(modelo)).get(0);
            cotizacionEncontrada = cotizacionRepository.save(new Cotizacion(vehiculo));
            vehiculo.agregarCotizacion(cotizacionEncontrada);
        }
        else {
            cotizacionEncontrada = this.encontrarCotizacion(cotizacion);
            vehiculo = cotizacionEncontrada.getVehiculo();
        }
        productoEncontrado = productoRepository.findByNombre(SpringApplicationCvds.stringStandar(producto)).get(0);
        cotizacionEncontrada.setEstado(Cotizacion.EN_PROCESO);
        cotizacionEncontrada.agregarProductoAlCarrito(productoEncontrado);
        productoRepository.save(productoEncontrado);
        vehiculoRepository.save(vehiculo);
        return cotizacionRepository.save(cotizacionEncontrada);
    }


    /**
     * Metodo que quita producto del carrito
     * @param producto, producto a quitar
     * @param cotizacion cotizacion de la cual se va a quitar el producto
     */
    public void quitarDelCarrito(Producto producto, Cotizacion cotizacion){
        cotizacion.eliminarProductoDelCarrito(producto);
        cotizacionRepository.save(cotizacion);
        productoRepository.save(producto);
    }


    /**
     * Funcion que investiga los productos que estan incluidos en el carrito de una cotizacion en especifico
     * @param cotizacionId, identificador del cotizador  a avegiruar
     * @return lita de productos
     */
    public List<Producto> verCarrito(Long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.getProductosCotizacion();

    }

    /**
     * Funcion que revisa si un producto esta en pesos colombianos, de no estarlo, convierte su valor a pesos colombianos
     * @param producto, producto  a analizar
     * @return valor del producto en pesos colombianos
     */
    private Money moneyConversion(Producto producto){
        if(!producto.getMoneda().equals("COP")) return convertidorCop(Money.of(producto.getValor(),producto.getMoneda()));
        else return Money.of(producto.getValor(),producto.getMoneda());
    }

    /**
     * Calcula el subtotal de los productos agregados al carrito
     * @param cotizacion, cotizacion que se desea realizar
     * @return Money del subtotal del carrito
     */
    public Money calcularTotalCarrito(Cotizacion cotizacion){
        Money total = Money.zero(Monetary.getCurrency("COP"));
        for (Producto producto : verCarrito(cotizacion.getIden())) {
            total = total.add(moneyConversion(producto));
        }
        return total;
    }

    /**
     * Calcula el total de una cotizacion teniendo en cuenta, subtotal del carrito, impuesto y descuento
     * @param cotizacion
     * @return total a pagar.
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
     * Metodo para separar cita a una cotizacion especifica
     * @param cita, fecha y hora en la que se va a realizar la cita
     * @param ciudad, ciudad donde dse va a recoger el vehiculo
     * @param direccion, direccion donde se va a recoger el vehiculo
     * @param cotizacion identificacion de la cotizacion a agendar
     * @param nombreCliente, nombre del cliente que agenda la cita
     * @param  apellidoCliente, apellido del cliente
     * @throws LincolnLinesException FECHA_NO_DISPONIBLE si la fecha que se desea agendar ya esta tomada.
     */
    public void agendarCita(LocalDateTime cita, String ciudad, String direccion, long cotizacion, String nombreCliente, String apellidoCliente) throws LincolnLinesException {
        if(!fechaDisponible(cita)) throw new LincolnLinesException(LincolnLinesException.FECHA_NO_DISPONIBLE);
        Cotizacion cotizacionEncontrada = this.encontrarCotizacion(cotizacion);
        Cliente cliente = clienteRepository.findByNombreAndApellido(nombreCliente,apellidoCliente).get(0);
        cotizacionEncontrada.agendar(cita,ciudad,direccion,cliente);
        clienteRepository.save(cliente);
        cotizacionRepository.save(cotizacionEncontrada);
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

    public void limpiarTabla(){
        cotizacionRepository.deleteAllInBatch();
    }

    /**
     * Funcion que indica cuantas veces se encuentra un producto en el carrito
     * @param nombrePorducto, producto que se desea analizar
     * @param cotizacion, carrito que se desea revisar
     * @return numero de veces que se encuentra el producto en el carrito
     */
    public int contadorProducto(String nombrePorducto,long cotizacion){
        int contador = 0;
        Producto producto = productoRepository.findByNombre(SpringApplicationCvds.stringStandar(nombrePorducto)).get(0);
        List<Producto> carrito = this.verCarrito(cotizacion);
        for(int i = 0; i < carrito.size();i++){
            if(carrito.get(i).equals(producto)) contador++;
        }
        return contador;
    }

    public Vehiculo getVehiculo(long cotizacion){
        Cotizacion encontrada = this.encontrarCotizacion(cotizacion);
        return encontrada.getVehiculo();
    }


}