package co.edu.eci.cvds.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "COTIZACIONES")
public class Cotizacion{
    @Id
    @Column(name = "NUMCOTIZACION")
    private int numCotizacion;

    @Column(name = "CEDULA")
    private String cedula;

    @Column(name = "DESCRIPCION")
    private String descripcion;


    public Cotizacion(){
    }

    public int getNumCotizacion() {
        return numCotizacion;
    }

    public void setNumCotizacion(int numCotizacion) {
        this.numCotizacion= numCotizacion;
    }

    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    


}