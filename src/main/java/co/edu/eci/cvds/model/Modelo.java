package co.edu.eci.cvds.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MODELO")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MODELO_ID")
    private int idModelo;
    @Column(name = "CILINDRAJE", nullable = false)
    private String cilindraje;
    @Column(name = "AÑO", nullable = false)
    private Integer año;
    
    
    
    public Modelo() {
    }



    public Modelo(int idModelo, String cilindraje, Integer año) {
        this.idModelo = idModelo;
        this.cilindraje = cilindraje;
        this.año = año;
    }



    public int getIdModelo() {
        return idModelo;
    }



    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }



    public String getCilindraje() {
        return cilindraje;
    }



    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }



    public Integer getAño() {
        return año;
    }



    public void setAño(Integer año) {
        this.año = año;
    }



    @Override
    public String toString() {
        return "Modelo [idModelo=" + idModelo + ", cilindraje=" + cilindraje + ", año=" + año + "]";
    }


    
}
