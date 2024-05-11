package co.edu.eci.cvds.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "SERVICIO")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICIO_ID")
    private Integer idServicio;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PRECIO")
    private double precio;
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    private Date fechaFin;
    @ManyToOne
    @JoinColumn(name = "CARRITO_ID")
    private Carrito carrito;
    @ManyToMany
    @JoinTable(name ="Servicio_Producto", joinColumns= @JoinColumn (name = "SERVICIO_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCTO_ID") )
    private List<Producto> productos;



    
    public Servicio() {
    }




    public Servicio(Integer idServicio, String descripcion, double precio, Date fechaInicio, Date fechaFin,
            Carrito carrito, List<Producto> productos) {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.carrito = carrito;
        this.productos = productos;
    }




    public Integer getIdServicio() {
        return idServicio;
    }




    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }




    public String getDescripcion() {
        return descripcion;
    }




    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




    public double getPrecio() {
        return precio;
    }




    public void setPrecio(double precio) {
        this.precio = precio;
    }




    public Date getFechaInicio() {
        return fechaInicio;
    }




    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }




    public Date getFechaFin() {
        return fechaFin;
    }




    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }




    public Carrito getCarrito() {
        return carrito;
    }




    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }




    public List<Producto> getProductos() {
        return productos;
    }




    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }


    
   
}
