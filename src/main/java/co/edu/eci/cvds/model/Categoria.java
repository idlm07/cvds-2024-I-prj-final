package co.edu.eci.cvds.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;

import java.util.Set;

/**
 * Clase - Entidad Categoria
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Entity
@Table(name = "Categorias")
public class Categoria {

    @Id
    @Column(name = "nombre", length = 30, nullable = false)
    @Getter private String nombre;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProductosPorCategoria",
            joinColumns = {
                    @JoinColumn(name = "nombre")
            },
            inverseJoinColumns = @JoinColumn(name = "producto", referencedColumnName = "nombre")
    )
    @Getter private Set<Producto> productosCategoria;

    public Categoria(){
        this.productosCategoria = new HashSet<>();
    }

    /**
     * Constructor de Categoria
     * @param nombre, nombre de la categoria
     */
    public Categoria(String nombre){
        this.nombre = nombre;
        this.productosCategoria = new HashSet<>();
    }

    /**
     * Metodo que agrega un producto a la lista de prodcutos
     * @param producto a agregar
     */
    public void agregarProducto(Producto producto){
        this.productosCategoria.add((producto));
    }

    /**
     * Metodo que elimina un producto de la lista de productosCategoria
     * @param producto a eliminar
     */
    public void eliminarProducto(Producto producto){
        this.productosCategoria.remove(producto);
    }




}
