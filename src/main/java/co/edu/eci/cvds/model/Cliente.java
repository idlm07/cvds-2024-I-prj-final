package co.edu.eci.cvds.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLIENTES")
public class Cliente {
    @Id
    @Column(name = "CEDULA")
    private int cedula;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TELEFONO")
    private long telefono;

    @Column(name = "CORREOELECTRONICO")
    private String correoElectronico;

    public Cliente() {
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }




}
