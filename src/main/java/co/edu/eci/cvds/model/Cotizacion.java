package co.edu.eci.cvds.model;

import co.edu.eci.cvds.exception.LincolnLinesException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;


import javax.money.Monetary;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase - Entidad Cotizacion
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Entity
@Table(name = "Cotizaciones")
public class Cotizacion {
    public static final String CREADO = "CREADO";
    public static final String EN_PROCESO = "EN PROCESO";
    public static final String FINALIZADO = "FINALIZADO";
    public static final String ELIMINADO = "ELIMINADO";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Getter private long iden;

    @Column(name = "estado", length = 10, nullable = false)
    @Getter @Setter private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion", nullable = false, updatable = false)
    @Getter private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cita")
    @Getter private LocalDateTime cita;

    @Column(name = "ciudadRecogida", length = 50)
    @Getter private String ciudadRecogida;
    @Column(name = "direccionRecogida", length = 50)
    @Getter private String direccionRecogida;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Carrito",
            joinColumns = @JoinColumn(name = "cotizacion"),
            inverseJoinColumns = @JoinColumn(name = "producto", referencedColumnName = "nombre")
    )
    @Getter private List<Producto> productosCotizacion;

    @ManyToOne
    @JoinColumn(name = "nombreCliente", referencedColumnName = "nombre")
    @JoinColumn(name = "apellidoCliente", referencedColumnName = "apellido")
    @Getter private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name = "marcaVehiculo")
    @JoinColumn(name = "modeloVehiculo")
    @Getter private Vehiculo vehiculo;



    public Cotizacion() {
        this.estado = Cotizacion.CREADO;
        this.fechaCreacion = LocalDateTime.now();
        this.productosCotizacion = new ArrayList<>();
    }

    /**
     * Constructor Cotizacion
     * @param vehiculo, vehiculo para el cual se realiza la cotizacion
     */
    public Cotizacion(Vehiculo vehiculo){
        this.vehiculo = vehiculo;
        this.estado = Cotizacion.CREADO;
        this.fechaCreacion = LocalDateTime.now();
        this.productosCotizacion = new ArrayList<>();

    }

    /**
     * Metodo que agrega un producto al carrito
     * @param producto, producto a agregar
     * @throws LincolnLinesException PRODUCTO_NO_COMPATIBLE si el producto no es apto para el vehiculo al cual se le esta haciendo la cotizacion.
     */
    public void agregarProductoAlCarrito(Producto producto) throws LincolnLinesException {
        if(!this.vehiculo.productoApto(producto)) throw  new LincolnLinesException(LincolnLinesException.PRODUCTO_NO_COMPATIBLE);
        if(cita != null) throw  new LincolnLinesException(LincolnLinesException.FECHA_CLONCLUIDA);
        this.setEstado(Cotizacion.EN_PROCESO);
        productosCotizacion.add(producto);

    }

    /**
     * Metodo para eliminar algun producto del carrito
     * @param producto, producto que se desea agregar.
     */
    public void eliminarProductoDelCarrito(Producto producto){
        productosCotizacion.remove(producto);

        if (this.getProductosCotizacion().isEmpty()) {
            this.setEstado(Cotizacion.ELIMINADO);
        }
    }

    /**
     * Metodo para programar una cita con TOPGEAR
     * @param cita, fecha de la cita
     * @param ciudadRecogida, ciudad en la que se va a recoger el vehiculo
     * @param direccionRecogida, direccion en la que se va a recoger el vehiculo
     * @param cliente, cliente que realiza la cita.
     * @throws LincolnLinesException DATOS_FALTANTES si no se proporciona una fecha o cliente o si,
     * el cliente decide que recojan el vehiculo y no proporciona los datos completos.
     * @throws LincolnLinesException COTIZACION_AGENDADA, si la cotizacion ya tiene una cita agendada
     * @throws  LincolnLinesException CARRITO_VACIO si el la cotizacion no tiene ningun producto
     */
    public void agendar(LocalDateTime cita, String ciudadRecogida, String direccionRecogida,Cliente cliente) throws LincolnLinesException {
        if(this.cita != null) throw new LincolnLinesException(LincolnLinesException.COTIZACION_AGENDADA);
        if(this.productosCotizacion.isEmpty()) throw new LincolnLinesException(LincolnLinesException.CARRITO_VACIO);
        if(cliente == null
                || (ciudadRecogida != null && direccionRecogida == null)
                || (direccionRecogida != null && ciudadRecogida == null)) throw new LincolnLinesException(LincolnLinesException.DATOS_FALTANTES);
        this.cita = cita;
        this.ciudadRecogida = ciudadRecogida;
        this.direccionRecogida = direccionRecogida;
        this.cliente = cliente;

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
     * @return Money del subtotal del carrito
     */
    public Money calcularTotalCarrito(){
        Money total = Money.zero(Monetary.getCurrency("COP"));
        for (Producto producto : this.getProductosCotizacion()) {
            total = total.add(moneyConversion(producto));
        }
        return total;
    }

    /**
     * Calcula el total de la cotizacion, teniendo en cuenta el subtotal, los descuentos e impuestos
     * @return float indicando el total final de la cotizaion
     */
    public float calcularFinal(){
        float totalDescuento ;
        float totalImpuesto ;
        Money total = this.calcularTotalCarrito();
        Money mTotalDescuento;
        Money mTotalImpuesto;
        for(Producto producto : productosCotizacion){
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
        this.estado = Cotizacion.FINALIZADO;
        return total.getNumber().floatValue();
    }

    public void limpiarCotizacion(){
        this.vehiculo = null;
        this.cliente = null;
        this.productosCotizacion.clear();
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (iden ^ (iden >>> 32));
        result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
        result = prime * result + ((cita == null) ? 0 : cita.hashCode());
        result = prime * result + ((ciudadRecogida == null) ? 0 : ciudadRecogida.hashCode());
        result = prime * result + ((direccionRecogida == null) ? 0 : direccionRecogida.hashCode());
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((vehiculo == null) ? 0 : vehiculo.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj){
        try{
            Cotizacion cotizacion = (Cotizacion) obj;

            return (this.iden == 0 ? cotizacion.getIden() == 0 :cotizacion.getIden() == this.iden) &&
                    (this.estado == null ? cotizacion.getEstado() == null : estado.equals(cotizacion.getEstado())) &&
                    fechaCreacion.equals(cotizacion.getFechaCreacion()) &&
                    (cita == null ? cotizacion.getCita() == null : cita.equals(cotizacion.getCita())) &&
                    (ciudadRecogida == null ? cotizacion.getCiudadRecogida() == null : ciudadRecogida.equals(cotizacion.getCiudadRecogida())) &&
                    (direccionRecogida == null ? cotizacion.getDireccionRecogida() == null : direccionRecogida.equals(cotizacion.getDireccionRecogida())) &&
                    (this.cliente == null ? cotizacion.getCliente() == null : cliente.equals(cotizacion.getCliente())) &&
                    (this.vehiculo == null ? cotizacion.getVehiculo() == null :vehiculo.equals(cotizacion.getVehiculo()));

        }catch(Exception e){
            return false;
        }

    }


}