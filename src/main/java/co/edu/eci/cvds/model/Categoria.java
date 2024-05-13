package co.edu.eci.cvds.model;

import co.edu.eci.cvds.SpringApplicationCvds;
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
        this.nombre = SpringApplicationCvds.stringStandar(nombre);
        this.productosCategoria = new HashSet<>();
    }

    /**
     * Metodo que agrega un producto a la lista de prodcutos
     * @param producto a agregar
     */
    public void agregarProducto(Producto producto){
        producto.agregarCategoria(this);
        this.productosCategoria.add((producto));
    }

    /**
     * Metodo que elimina un producto de la lista de productosCategoria
     * @param producto a eliminar
     */
    public void eliminarProducto(Producto producto){
        this.productosCategoria.remove(producto);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((productosCategoria == null) ? 0 : productosCategoria.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Categoria categoria = (Categoria) obj;

            return (this.nombre == null ? categoria.getNombre() == null : this.nombre.equals(categoria.getNombre()))
                    && this.productosCategoria.equals(categoria.getProductosCategoria());
        } catch (Exception e) {
            return false;
        }
    }





}