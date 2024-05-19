package co.edu.eci.cvds.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTO_ID")
    private Integer idProducto;
    @Column(name = "ARCA", nullable = false)
    private String marca;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Column(name = "PRECIO", nullable = false)
    private double precio;
    @ManyToMany(mappedBy = "productos" )
    private List<Servicio> servicios;

    

    public Producto() {
    }



    public Producto(Integer idProducto, String marca, String descripcion, double precio, List<Servicio> servicio) {
        this.idProducto = idProducto;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.servicios = servicio;
    }



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


    public List<Servicio> getServicio() {
        return servicios;
    }



    public void setServicio(List<Servicio> servicio) {
        this.servicios = servicio;
    }


    
    
}
