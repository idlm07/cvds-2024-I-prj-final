package co.edu.eci.cvds.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "VEHICULO")

public class Vehiculo {
    @Id
    @Column(name = "MARCA")
    private String marca;
    @Column(name = "MODELO_ID")
    private String idModelo;
    @Column(name = "CLIENTE_ID")
    private String idCliente;


    
    public Vehiculo() {
    }



    public Vehiculo(String marca, String idModelo, String idCliente) {
        this.marca = marca;
        this.idModelo = idModelo;
        this.idCliente = idCliente;
    }



    public String getMarca() {
        return marca;
    }



    public void setMarca(String marca) {
        this.marca = marca;
    }



    public String getIdModelo() {
        return idModelo;
    }



    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }



    public String getIdCliente() {
        return idCliente;
    }



    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }



    @Override
    public String toString() {
        return "Vehiculo [marca=" + marca + ", idModelo=" + idModelo + ", idCliente=" + idCliente + "]";
    }


    
    

}
