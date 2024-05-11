package co.edu.eci.cvds.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "Clientes",
        uniqueConstraints = {@UniqueConstraint(name = "UK_Clientes", columnNames = {"nombre","primerApellido","segundoApellido"})}
)
public class Cliente {
    @Id
    @Column(name = "correo", length = 50, nullable = false)
    @Getter @Setter private String correo;
    @Column(name = "nombre", length = 50,nullable = false)
    @Getter @Setter private String nombre;
    @Column(name = "primerApellido", length = 50, nullable = false)
    @Getter @Setter private String primerApellido;
    @Column(name = "segundoApellido",length = 50)
    @Getter @Setter private String segundoApellido;
    @Column(name = "celular", length = 10, nullable = false)
    @Getter @Setter private String celular;
    @OneToMany(mappedBy = "cliente")
    private Set<Cotizacion> cotizaciones;

    public Cliente() {
        cotizaciones = new HashSet<>();
    }

    public Cliente(String correo, String nombre, String primerApellido, String segundoApellido, String celular){
        this.correo = correo;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.celular = celular;
        this.cotizaciones = new HashSet<>();
    }

    public void agregarCotizacion(Cotizacion cotizacion){this.cotizaciones.add(cotizacion);}

    public void eliminarCotizacion(Cotizacion cotizacion){this.cotizaciones.remove(cotizacion);}

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((primerApellido == null) ? 0 : primerApellido.hashCode());
        result = prime * result + ((segundoApellido == null) ? 0 : segundoApellido.hashCode());
        result = prime * result + ((celular == null) ? 0 : celular.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        try{
            Cliente client = (Cliente) obj;
            return correo.equals(client.getCorreo())
                    && nombre.equals(client.getNombre())
                    && primerApellido.equals(client.getPrimerApellido())
                    && celular.equals(client.getCelular())
                    && this.hashCode() == client.hashCode();

        }catch (Exception e){
            return false;
        }
    }

}
