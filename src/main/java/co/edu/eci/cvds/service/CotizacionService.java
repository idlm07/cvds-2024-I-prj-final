package co.edu.eci.cvds.service;


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
            vehiculo = vehiculoRepository.findByMarcaAndModel(marca.toUpperCase(), modelo.toUpperCase()).get(0);
            cotizacionEncontrada = cotizacionRepository.save(new Cotizacion(vehiculo));
            vehiculo.agregarCotizacion(cotizacionEncontrada);
        }
        else {
            cotizacionEncontrada = this.encontrarCotizacion(cotizacion);
            vehiculo = cotizacionEncontrada.getVehiculo();
        }
        productoEncontrado = productoRepository.findByNombre(producto.toUpperCase()).get(0);
        cotizacionEncontrada.setEstado(Cotizacion.EN_PROCESO);
        cotizacionEncontrada.agregarProductoAlCarrito(productoEncontrado);
        productoRepository.save(productoEncontrado);
        vehiculoRepository.save(vehiculo);
        return cotizacionRepository.save(cotizacionEncontrada);
    }


    /**
     * Metodo que quita producto del carrito
     * @param nombreProducto, nombre del producto a quitar del carrito
     * @param cotizacionId identificador de la cotizacion de la cual se va a quitar el producto
     */
    public void quitarDelCarrito(String nombreProducto, long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        Producto producto = productoRepository.findByNombre(nombreProducto.toUpperCase()).get(0);
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
     * indica el Subtotal de los productos agregados al carrito de cierta cotizacion
     * @param cotizacionId, identificador de la cotizacion en la que se agregaron los productos
     * @return Money del subtotal del carrito
     */
    public float calcularTotalCarrito(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularTotalCarrito().getNumber().floatValue();
    }

    /**
     * Entrega la cotizacion final de una cotizacion, teniendo en cuenta subtotal, descuento e impuestos
     * @param cotizacionId, identificador de la cotizacion.
     * @return total a pagar.
     */


    public float calcularFinal(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularFinal();
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
        Producto producto = productoRepository.findByNombre(nombrePorducto.toUpperCase()).get(0);
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