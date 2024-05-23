package co.edu.eci.cvds.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="CLIENTE") 
public class Cliente {
    @Id
    @Column(name = "CLIENTE_ID")
    private Integer idCliente; //cedula usuario
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "CORREO", nullable = false)
    private String correo;
    @Column(name = "TELEFONO", nullable = false)
    private String telefono;
    @Column(name = "MARCA", nullable = false)
    private String marca;

    
    


    public Cliente() {
    }

    


    public Cliente(Integer idCliente, String nombre, String correo, String telefono, String marca) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.marca = marca;
    }




    public Integer getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getMarca() {
        return marca;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }




    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", correo=" + correo + ", telefono="
                + telefono + ", marca=" + marca + "]";
    }


   

    
}
