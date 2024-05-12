package co.edu.eci.cvds.model;

import co.edu.eci.cvds.Exception.LincolnLinesException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase - Entidad Producto
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */


@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @Column(name = "nombre", length = 50, nullable = false)
    @Getter @Setter private String nombre;
    @Column(name = "descripcionBreve", length = 150)
    @Getter @Setter private String descripcionBreve;
    @Column(name = "descripcionTecnica", length = 250)
    @Getter @Setter private String descripcionTecnica;
    @Column(name = "imagen", length = 100)
    @Getter @Setter private String imagen;
    @Column(name = "valor", nullable = false)
    @Getter  private float valor;
    @Column(name = "moneda", length = 10, nullable = false)
    @Getter @Setter private String moneda;
    @Column(name = "descuento",nullable = false)
    @Getter private float descuento;
    @Column(name = "impuesto",nullable = false)
    @Getter  private float impuesto;
    @ManyToMany(mappedBy = "productosVehiculo")
    @Getter private Set<Vehiculo> vehiculos;
    @ManyToMany(mappedBy = "productosCotizacion")
    @Getter private Set<Cotizacion> cotizaciones;
    @ManyToMany(mappedBy = "productosCategoria")
    @Getter private Set<Categoria> categorias;


    public Producto() {
        this.vehiculos = new HashSet<>();
        this.cotizaciones = new HashSet<>();
        this.categorias = new HashSet<>();
    }

    /**
     * Constructor de la clase producto
     * @param nombre, nombre del producto
     * @param categoria, categoria principal a la que pertence el producto
     * @param valor, valor de dicho producto
     * @param moneda, tipo de divisa
     */
    public Producto(String nombre, String categoria,float valor, String moneda) throws LincolnLinesException {
        this.setValor(valor);
        this.nombre = nombre;
        this.moneda = moneda;
        this.descuento = 0;
        this.impuesto = 0;
        this.vehiculos = new HashSet<>();
        this.cotizaciones = new HashSet<>();
        this.categorias = new HashSet<>();
        this.agregarCategoria(new Categoria(categoria));
    }

    /**
     * Agrega un vehiculo al conjunto de vehiculos
     * @param vehiculo a agregar
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    /**
     * Metodo que agregar cotizacion al conjunto de cotizaciones
     * @param cotizacion a agregar
     */
    public void agregarCotizacion(Cotizacion cotizacion) {
        this.cotizaciones.add(cotizacion);
    }

    /**
     * Metodo para eliminar cotizacion del conjunto de cotizaciones
     * @param cotizacion a eliminar
     */
    public void eliminarCotizacion(Cotizacion cotizacion){this.cotizaciones.remove(cotizacion);}

    /**
     * Setter de valor
     * @param valor, nuevo valor del producto
     * @throws LincolnLinesException VALOR_NEGATIVO si el nuevo valor es negativo
     */
    public void setValor(float valor) throws LincolnLinesException {
        if(valor < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        this.valor = valor;
    }

    /**
     * Setter de descuento
     * @param descuento, nuevo descuento del producto
     * @throws LincolnLinesException VALOR_NEGATIVO si el nuevo descuento es negativo
     */

    public void setDescuento(float descuento) throws LincolnLinesException {
        if(descuento < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        this.descuento = descuento;
    }

    /**
     * Setter de impuesto
     * @param impuesto, nuevo impuesto del producto
     * @throws LincolnLinesException VALOR_NEGATIVO si el nuevo impuesto es negativo
     */

    public void setImpuesto(float impuesto) throws LincolnLinesException {
        if(impuesto < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        this.impuesto = impuesto;
    }

    /**
     * metodo que asocia una categoria con el producto
     * @param categoria nueva categoria a la que pertenece el producto.
     */

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
        categoria.agregarProducto(this);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((descripcionBreve == null) ? 0 : descripcionBreve.hashCode());
        result = prime * result + ((descripcionTecnica == null) ? 0 : descripcionTecnica.hashCode());
        result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
        result = prime * result + Float.floatToIntBits(valor);
        result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
        result = prime * result + Float.floatToIntBits(descuento);
        result = prime * result + Float.floatToIntBits(impuesto);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        try {
            Producto producto = (Producto) obj;

            return this.nombre.equals(producto.getNombre())
                    && this.valor == producto.getValor()
                    && this.moneda.equals(producto.getMoneda())
                    && this.hashCode() == producto.hashCode();
        } catch (Exception e) {
            return false;
        }
    }



}
