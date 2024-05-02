package co.edu.eci.cvds.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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
    @Column(name = "categoria", length = 20, nullable = false)
    @Getter @Setter private String categoria;
    @Column(name = "imagen", length = 100)
    @Getter @Setter private String imagen;
    @Column(name = "valor", nullable = false)
    @Getter @Setter private float valor;
    @Column(name = "moneda", length = 10, nullable = false)
    @Getter @Setter private String moneda;
    @Column(name = "descuento",nullable = false)
    @Getter @Setter private float descuento;
    @Column(name = "impuesto",nullable = false)
    @Getter @Setter private float impuesto;
    @ManyToOne
    @JoinColumn(name = "marcaVehiculo", referencedColumnName = "marca",nullable = false)
    @JoinColumn(name = "modeloVehiculo", referencedColumnName = "modelo",nullable = false)
    @JoinColumn(name = "yearVehiculo", referencedColumnName = "year",nullable = false)

    private Vehiculo vehiculo;


    public Producto() {}
    public Producto(String nombre,String categoria, float valor, String moneda, Vehiculo vehiculo) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.valor = valor;
        this.moneda = moneda;
        this.descuento = 0;
        this.impuesto = 0;
        this.vehiculo = vehiculo;
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

    public boolean equals(Object obj) {
        try {
            Producto producto = (Producto) obj;
            return this.nombre.equals(producto.getNombre()) &&
                    this.descripcionBreve.equals(producto.getDescripcionBreve())
                    && this.descripcionTecnica.equals(producto.getDescripcionTecnica())
                    && this.categoria.equals(producto.getCategoria())
                    && this.imagen.equals(producto.getImagen())
                    && this.valor == producto.getValor()
                    && this.moneda.equals(producto.getMoneda())
                    && this.descuento == producto.getDescuento()
                    && this.impuesto == producto.getImpuesto();
        } catch (Exception e) {
            return false;
        }
    }
}
