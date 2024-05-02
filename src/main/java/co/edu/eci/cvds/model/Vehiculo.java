package co.edu.eci.cvds.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vehiculos")
public class Vehiculo {
    @Id
    @Column(name = "marca", length = 20, nullable = false)
    @Getter @Setter private String marca;
    @Id
    @Column(name = "modelo", length = 20, nullable = false)
    @Getter @Setter private String model;
    @Id
    @Column(name = "year", length = 4, nullable = false)
    @Getter @Setter private String year;
    @ManyToMany
    @JoinTable(
            name = "ProductosPorVehiculo",
            joinColumns = {
                    @JoinColumn(name = "marca"),
                    @JoinColumn(name = "modelo"),
                    @JoinColumn(name = "year")
            },
            inverseJoinColumns = @JoinColumn(name = "nombreProducto", referencedColumnName = "nombre")
    )
    private ArrayList<Producto> productos = new ArrayList<>();



    public Vehiculo(String marca, String model, String year) {
        this.marca = marca;
        this.model = model;
        this.year = year;
    }

    public Vehiculo() {}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        try {
            Vehiculo producto = (Vehiculo) obj;
            return marca.equals(producto.getMarca()) && model.equals(producto.getModel()) && year.equals(producto.getYear());
        } catch (Exception e) {
            return false;
        }
    }









}
