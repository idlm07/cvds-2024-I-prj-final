package co.edu.eci.cvds.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import co.edu.eci.cvds.ID.VehiculoID;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Vehiculos")
@IdClass(VehiculoID.class)

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
    @Getter private List<Producto> productosVehiculo;



    public Vehiculo(String marca, String model, String year) {
        this.marca = marca;
        this.model = model;
        this.year = year;
        this.setProductosVehiculo(new ArrayList<>());
    }

    public Vehiculo() {
        this.productosVehiculo = new ArrayList<>();
    }

    public void setProductosVehiculo(List<Producto> productosVehiculo) {
        this.productosVehiculo = productosVehiculo;
    }

    public void anadirProducto(Producto producto){
        productosVehiculo.add(producto);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        try {
            Vehiculo producto = (Vehiculo) obj;
            return marca.equals(producto.getMarca()) && model.equals(producto.getModel()) && year.equals(producto.getYear());
        } catch (Exception e) {
            return false;
        }
    }









}
