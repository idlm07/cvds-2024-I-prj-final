package co.edu.eci.cvds.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import co.edu.eci.cvds.ID.VehiculoID;
import java.util.HashSet;
import java.util.Set;


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
    @Column(name = "yearVehicle", length = 4, nullable = false)
    @Getter @Setter private String yearVehicle;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Productos_Por_Vehiculo",
            joinColumns = {
                    @JoinColumn(name = "marca"),
                    @JoinColumn(name = "modelo")
            },
            inverseJoinColumns = @JoinColumn(name = "nombreProducto", referencedColumnName = "nombre")
    )
    @Getter private Set<Producto> productosVehiculo;

    @OneToMany(mappedBy = "vehiculo")
    @Getter private Set<Cotizacion> cotizaciones;



    public Vehiculo(String marca, String model, String year) {
        this.marca = marca;
        this.model = model;
        this.yearVehicle = year;
        this.productosVehiculo = new HashSet<>();
        this.cotizaciones = new HashSet<>();
    }

    public Vehiculo() {
        this.cotizaciones = new HashSet<>();
        this.productosVehiculo = new HashSet<>();
    }



    public void anadirProducto(Producto producto){
        productosVehiculo.add(producto);
    }
    public void eliminarProducto (Producto producto){productosVehiculo.remove(producto);}
    public void agregarCotizacion(Cotizacion cotizacion){
        cotizaciones.add(cotizacion);
    }

    public boolean productoApto(Producto producto){

        return productosVehiculo.contains(producto);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((yearVehicle == null) ? 0 : yearVehicle.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        try {
            Vehiculo vehiculo = (Vehiculo) obj;
            return marca.equals(vehiculo.getMarca()) && model.equals(vehiculo.getModel()) && yearVehicle.equals(vehiculo.getYearVehicle()) && this.hashCode() == vehiculo.hashCode();
        } catch (Exception e) {
            return false;
        }
    }









}
