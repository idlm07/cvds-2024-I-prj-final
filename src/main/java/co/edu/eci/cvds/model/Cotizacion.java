package co.edu.eci.cvds.model;

import co.edu.eci.cvds.Exception.LincolnLinesException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



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
    @Getter @Setter private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "marcaVehiculo")
    @JoinColumn(name = "modeloVehiculo")
    @Getter @Setter private Vehiculo vehiculo;



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
        this.vehiculo.agregarCotizacion(this);
    }

    /**
     * Metodo que agrega un producto al carrito
     * @param producto, producto a agregar
     * @throws LincolnLinesException PRODUCTO_NO_COMPATIBLE si el producto no es apto para el vehiculo al cual se le esta haciendo la cotizacion.
     */
    public void agregarProductoAlCarrito(Producto producto) throws LincolnLinesException {
        if(!this.vehiculo.productoApto(producto)) throw  new LincolnLinesException(LincolnLinesException.PRODUCTO_NO_COMPATIBLE);
        productosCotizacion.add(producto);
        producto.agregarCotizacion(this);
    }

    /**
     * Metodo para eliminar algun producto del carrito
     * @param producto, producto que se desea agregar.
     */
    public void eliminarProductoDelCarrito(Producto producto){
        productosCotizacion.remove(producto);
        producto.eliminarCotizacion(this);
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
     * el cliente decide que recojan el vehiculo y no proporciona los datos completos
     */
    public void agendar(LocalDateTime cita, String ciudadRecogida, String direccionRecogida,Cliente cliente) throws LincolnLinesException {
        if(cita == null || cliente == null
                || (ciudadRecogida != null && direccionRecogida == null)
                || (direccionRecogida != null && ciudadRecogida == null)) throw new LincolnLinesException(LincolnLinesException.DATOS_FALTANTES);
        this.cita = cita;
        this.ciudadRecogida = ciudadRecogida;
        this.direccionRecogida = direccionRecogida;
        this.cliente = cliente;
        cliente.agregarCotizacion(this);
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
            return cotizacion.getIden() == this.iden && this.hashCode() == cotizacion.hashCode();
        }catch(Exception e){
            return false;
        }

    }
}