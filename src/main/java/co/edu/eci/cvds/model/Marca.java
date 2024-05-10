package co.edu.eci.cvds.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MARCAS")
public class Marca {
    @Id
    @Column(name = "MARCA")
    private String marca;

    @Column(name = "PAIS")
    private String pais;
    

    public Marca(){
    }
    
    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

}