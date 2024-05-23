package co.edu.eci.cvds.model;

import java.util.List;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "REVISION")
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVISION_ID")
    private Integer idRevision;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "CARRITO_ID")
    private Carrito carrito;

    @ManyToMany
    @JoinTable(name ="REVISION_SERVICIO", joinColumns= @JoinColumn(name = "REVISION_ID"), inverseJoinColumns = @JoinColumn(name = "SERVICIO_ID"))
    private List<Servicio> servicios;

    public Revision() {
    }

    public Revision(Integer idRevision, double precio, String descripcion, Carrito carrito, List<Servicio> servicios) {
        this.idRevision = idRevision;
        this.precio = precio;
        this.descripcion = descripcion;
        this.carrito = carrito;
        this.servicios = servicios;
    }

    public Integer getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Integer idRevision) {
        this.idRevision = idRevision;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
}
