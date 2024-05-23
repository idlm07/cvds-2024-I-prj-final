package co.edu.eci.cvds.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CARRITOS_DE_COMPRAS") // Corrige el nombre de la tabla
public class Carrito {
    @Id
    @Column(name = "CLIENTE_ID")
    private String idCliente;

    @Column(name = "CARRITO_ID")
    private String idCarrito;

    @Column(name = "FECHA_DEL_PEDIDO", nullable = false)
    private Date fechaPedido;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "TOTAL", nullable = false)
    private double total;

    @OneToMany(mappedBy = "carrito")
    private List<Revision> revisiones;

    public Carrito() {
    }

    public Carrito(String idCliente, String idCarrito, Date fechaPedido, String estado, double total) {
        this.idCliente = idCliente;
        this.idCarrito = idCarrito;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
        this.revisiones = new ArrayList<>();
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Revision> getRevisiones() {
        return revisiones;
    }

    public void setRevisiones(List<Revision> revisiones) {
        this.revisiones = revisiones;
    }

    public List<Carrito> getProductos() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getProductos'");
    }

    public Integer getId() {
       
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public void setId(int i) {
        
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
