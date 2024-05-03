package co.edu.eci.cvds.model;

import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompras {
    private List<Producto> productos;

    public CarritoDeCompras() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void quitarProducto(Producto producto) {
        productos.remove(producto);
    }
    public float calcularSubtotal() {
        float subtotal = 0;
        for (Producto producto : productos) {
            subtotal += producto.getValor();
        }
        return subtotal;
    }

    public float calcularImpuestos(float tasaImpuesto) {
        return calcularSubtotal() * tasaImpuesto / 100;
    }

    public float calcularTotal(float tasaImpuesto) {
        float impuestos = calcularImpuestos(tasaImpuesto);
        return calcularSubtotal() + impuestos;
    }

    
}
