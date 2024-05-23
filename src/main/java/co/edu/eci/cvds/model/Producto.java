package co.edu.eci.cvds.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTO_ID")
    private Integer idProducto;

    @Column(name = "MARCA", nullable = false)
    private String marca;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private double precio;

    @ManyToMany(mappedBy = "productos")
    private List<Servicio> servicios;

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(Integer idProducto, String marca, String descripcion, double precio, List<Servicio> servicios) {
        this.idProducto = idProducto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.servicios = servicios;
    }

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
}
