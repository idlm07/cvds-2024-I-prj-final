package co.edu.eci.cvds.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTOS")
public class Producto {
    @Id
    @Column(name = "PRODUCTO")
    private String producto;

    @Column(name = "MARCA")
    private String marca;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "PRECIO")
    private int precio;


    public Producto() {
    }


    public String getProducto() {
        return producto;
    }


    public void setProducto(String producto) {
        this.producto = producto;
    }


    public String getMarca() {
        return marca;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public int getPrecio() {
        return precio;
    }


    public void setPrecio(int precio) {
        this.precio = precio;
    }

    
}