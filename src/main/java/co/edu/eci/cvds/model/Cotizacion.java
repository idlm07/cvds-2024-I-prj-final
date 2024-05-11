package co.edu.eci.cvds.model;

import co.edu.eci.cvds.Exception.LincolnLinesException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    @JoinColumn(name = "correoCliente", referencedColumnName = "correo")
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

    public Cotizacion(LocalDateTime cita, String ciudadRecogida, String direccionRecogida, Cliente cliente, Vehiculo vehiculo){
        this.estado = Cotizacion.CREADO;
        this.fechaCreacion = LocalDateTime.now();
        this.cita = cita;
        this.ciudadRecogida = ciudadRecogida;
        this.direccionRecogida = direccionRecogida;
        this.cliente = cliente;
        this.productosCotizacion = new ArrayList<>();
        this.vehiculo = vehiculo;
    }

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
        productosCotizacion.add(producto);
    }

    public void eliminarProductoDelCarrito(Producto producto){
        productosCotizacion.remove(producto);
    }

    public void agendar(LocalDateTime cita, String ciudadRecogida, String direccionRecogida){
        this.cita = cita;
        this.ciudadRecogida = ciudadRecogida;
        this.direccionRecogida = direccionRecogida;
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