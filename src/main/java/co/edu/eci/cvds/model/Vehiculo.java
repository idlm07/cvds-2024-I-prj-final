package co.edu.eci.cvds.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import co.edu.eci.cvds.ID.VehiculoID;
import java.util.HashSet;
import java.util.Set;
/**
 * Clase - Entidad Vehiculo
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

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


    /**
     * Constructor de Vehiculo
     * @param marca, marca del vehiculo
     * @param model, modelo del vehiculo
     * @param year, año en el que se estreno el vehiculo
     */
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


    /**
     * Asocia un producto con el vehiculo
     * @param producto, producto apto para el vehiculo
     */
    public void anadirProducto(Producto producto){
        productosVehiculo.add(producto);
    }

    /**
     * Aoscia una cotizacion con el vehiculo
     * @param cotizacion, cotizacion que se realiza para el vehiculo
     */
    public void agregarCotizacion(Cotizacion cotizacion){
        cotizaciones.add(cotizacion);
    }

    /**
     * Funcion que indica si un producto es apto para el vehiculo
     * @param producto, producto que se desea saber si es apto para el vehiculo.
     * @return true si el producto pertenece al conjunto de productos y false, de lo contrario
     */
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
